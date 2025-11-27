package com.mycompany.javaweb.dao;

import com.mycompany.javaweb.context.DBContext;
import com.mycompany.javaweb.entity.Order;
import com.mycompany.javaweb.dao.AdminDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    // Lấy danh sách tất cả đơn hàng
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders ORDER BY ngayTao DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }

        } catch(SQLException e) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách đơn hàng", e);
        }

        return orders;
    }

    // Cập nhật đơn hàng (phone và trạng thái)
    public void updateOrder(String orderId, String phone, String status) {
        String query = "UPDATE Orders SET soDienThoai=?, trangThai=? WHERE order_id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, phone);
            ps.setString(2, status);
            ps.setString(3, orderId);
            ps.executeUpdate();

        } catch(SQLException e) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật đơn hàng", e);
        }
    }

    // Xóa đơn hàng
    public boolean deleteOrder(String orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        boolean deleted = false;
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, orderId);
            deleted = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, "Lỗi khi xóa order với order_id: " + orderId, e);
        }

        return deleted;




    }
    // OrderDAO.java (Thêm vào class này)

    public List<Order> getOrdersByCustomer(long maKH) {
        List<Order> orders = new ArrayList<>();
        // Kiểm tra chính xác tên cột 'maKH' trong bảng Orders của bạn
        String query = "SELECT * FROM Orders WHERE maKH = ? ORDER BY ngayTao DESC";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy orders theo maKH", e);
        }
        return orders;
    }

    // Map ResultSet -> Order object
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderNumber(rs.getString("order_id"));
        o.setUserEmail(rs.getString("email"));
        o.setSubtotal(rs.getLong("subtotal"));
        o.setShipping(rs.getLong("shipping"));
        o.setTotal(rs.getLong("tongTien"));
        o.setCreatedDate(rs.getTimestamp("ngayTao"));
        o.setStatus(rs.getString("trangThai"));
        o.setShippingAddress(rs.getString("diaChi"));
        o.setPhone(rs.getString("soDienThoai"));
        o.setPaymentMethod(rs.getString("phuongThucThanhToan"));
        o.setItems(null); // Load sau nếu cần
        return o;
    }
}