package com.mycompany.javaweb.dao;

import com.mycompany.javaweb.context.DBContext;
import com.mycompany.javaweb.entity.CartItem;
import com.mycompany.javaweb.entity.Order;
import com.mycompany.javaweb.entity.Product;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    // LẤY DANH SÁCH ORDER THEO maKH

    public List<Order> getOrdersByCustomer(long maKH) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE maKH = ? ORDER BY ngayTao DESC";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, maKH);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getString("order_id"),
                        rs.getString("email"),
                        getOrderItems(rs.getString("order_id")),
                        rs.getLong("subtotal"),
                        rs.getLong("shipping"),
                        rs.getLong("tongTien"),
                        rs.getTimestamp("ngayTao"),
                        rs.getString("trangThai"),
                        rs.getString("diaChi"),
                        rs.getString("soDienThoai"),
                        rs.getString("phuongThucThanhToan")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi lấy danh sách orders", e);
        } finally {
            closeConnections();
        }

        return orders;
    }

    // --- LẤY ORDER THEO ORDER NUMBER ---
    public Order getOrderByNumber(String orderNumber) {
        Order order = null;
        String query = "SELECT * FROM Orders WHERE order_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, orderNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order(
                        rs.getString("order_id"),
                        rs.getString("user_email"),
                        getOrderItems(rs.getString("order_id")),
                        rs.getLong("subtotal"),
                        rs.getLong("shipping"),
                        rs.getLong("total"),
                        rs.getTimestamp("created_date"),
                        rs.getString("status"),
                        rs.getString("shipping_address"),
                        rs.getString("phone"),
                        rs.getString("payment_method")
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi lấy order", e);
        } finally {
            closeConnections();
        }
        return order;
    }

    // --- TẠO ORDER MỚI ---
    public void createOrder(Order order, long maKH) {
        String insertOrder = "INSERT INTO Orders(order_id, maKH, email, subtotal, shipping, tongTien, ngayTao, trangThai, diaChi, soDienThoai, phuongThucThanhToan) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(insertOrder);
            ps.setString(1, order.getOrderNumber());
            ps.setLong(2, maKH);
            ps.setString(3, order.getUserEmail());
            ps.setLong(4, order.getSubtotal());
            ps.setLong(5, order.getShipping());
            ps.setLong(6, order.getTotal());
            ps.setTimestamp(7, new java.sql.Timestamp(order.getCreatedDate().getTime()));
            ps.setString(8, order.getStatus());
            ps.setString(9, order.getShippingAddress());
            ps.setString(10, order.getPhone());
            ps.setString(11, order.getPaymentMethod());
            ps.executeUpdate();

            // --- Thêm chi tiết đơn hàng ---
            for (CartItem item : order.getItems()) {
                String insertItem = "INSERT INTO OrderItems(order_id, maSP, soLuong, kichCo, mauSac, donGia, thanhTien) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement psItem = conn.prepareStatement(insertItem)) {
                    psItem.setString(1, order.getOrderNumber());
                    psItem.setLong(2, item.getProductId());
                    psItem.setInt(3, item.getQuantity());
                    psItem.setString(4, item.getSize());
                    psItem.setString(5, item.getColor());
                    psItem.setLong(6, order.getSubtotal());
                    psItem.setLong(7, order.getTotal());
                    psItem.executeUpdate();
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi tạo order", e);
        } finally {
            closeConnections();
        }
    }

    // --- LẤY DANH SÁCH CART ITEM THEO ORDER ---
    public List<CartItem> getOrderItems(String orderNumber) {
        List<CartItem> list = new ArrayList<>();
        String query = "SELECT oi.*, sp.* FROM OrderItems oi "
                + "JOIN SanPham sp ON oi.maSP = sp.maSP "
                + "WHERE oi.order_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, orderNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = mapResultSetToProduct(rs);
                CartItem item = new CartItem();
                item.setProductId(rs.getLong("maSP"));
                item.setQuantity(rs.getInt("soLuong"));
                item.setSize(rs.getString("size"));
                item.setColor(rs.getString("color"));
                item.setProduct(p);
                list.add(item);
            }
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi lấy cart items", e);
        } finally {
            closeConnections();
        }
        return list;
    }

    // --- KIỂM TRA TỒN KHO ---
    public boolean hasEnoughStock(long productId, int quantity) {
        String query = "SELECT soLuong FROM SanPham WHERE maSP = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("soLuong") >= quantity;
            }
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi kiểm tra tồn kho", e);
        } finally {
            closeConnections();
        }
        return false;
    }

    // Giảm tồn kho cho các sản phẩm trong giỏ hàng khi thanh toán
    public void reduceStockAfterCheckout(List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            long productId = cartItem.getProductId();
            int quantity = cartItem.getQuantity();

            // Giảm số lượng sản phẩm trong giỏ hàng
            reduceStock(productId, quantity);  // Gọi lại phương thức reduceStock đã có
        }
    }

    // Giảm tồn kho khi thanh toán
    public void reduceStock(long productId, int quantity) {
        String query = "UPDATE SanPham SET soLuong = soLuong - ?, daBan = daBan + ? WHERE maSP = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setLong(3, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi giảm tồn kho", e);
        } finally {
            closeConnections();
        }
    }

    // --- CẬP NHẬT TRẠNG THÁI THANH TOÁN CỦA ĐƠN HÀNG ---
    public void updatePaymentStatus(String orderNumber, String paymentStatus) {
        String query = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, paymentStatus);  // Trạng thái thanh toán, ví dụ: "Đã thanh toán"
            ps.setString(2, orderNumber);    // Mã đơn hàng
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi cập nhật trạng thái thanh toán", e);
        } finally {
            closeConnections();
        }
    }


    // --- XÓA GIỎ HÀNG CỦA USER ---
    public void clearCart(long customerId) {
        String query = "DELETE FROM GioHang WHERE maKH = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, customerId);  // Thay vì email, bạn truyền customerId
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi xóa giỏ hàng", e);
        } finally {
            closeConnections();
        }
    }


    // --- ÁNH XẠ ResultSet -> Product ---
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getLong("maSP"));
        p.setCategoryId(rs.getLong("maDM"));
        p.setBrandId(rs.getLong("maTH"));
        p.setName(rs.getString("ten"));
        p.setDescription(rs.getString("moTa"));
        p.setPrice(rs.getDouble("gia"));
        p.setStock(rs.getInt("soLuong"));
        p.setSizes(rs.getString("kichCo"));
        p.setColors(rs.getString("mauSac"));
        p.setMaterial(rs.getString("chatLieu"));
        p.setImage(rs.getString("hinhAnh"));
        p.setSold(rs.getInt("daBan"));
        return p;
    }

    // --- ĐÓNG KẾT NỐI ---
    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, "Lỗi đóng kết nối", e);
        }
    }
}