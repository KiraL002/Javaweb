package com.mycompany.javaweb.dao;

import com.mycompany.javaweb.context.DBContext;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Brand;
import com.mycompany.javaweb.entity.CartItem;
import com.mycompany.javaweb.entity.Category;
import com.mycompany.javaweb.entity.Order;
import com.mycompany.javaweb.entity.Product;
import entity.Review;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // ======================================================================
    // === 1. NGUYỄN TUẤN KIỆT (Trang chủ, Giỏ hàng, Chi tiết SP, Đăng nhập/ký, Báo cáo)
    // ======================================================================

    /** Nhóm TRANG CHỦ & CHI TIẾT SẢN PHẨM (Được chia sẻ, nhưng là giao diện chính của user) */
    
    // Sản phẩm mới nhất (Home Page)
    public List<Product> getNewProducts() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM SanPham ORDER BY maSP DESC LIMIT 4";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi getNewProducts", e);
        } finally {
            closeConnections();
        }
        return list;
    }

    // Sản phẩm bán chạy nhất (Home Page)
    public List<Product> getBestSellerProducts() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM SanPham ORDER BY daBan DESC LIMIT 4";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi getBestSellerProducts", e);
        } finally {
            closeConnections();
        }
        return list;
    }

    public Product getProductById(long id) {
        String query = "SELECT * FROM SanPham WHERE maSP = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return null;
    }

    /** Nhóm GIỎ HÀNG */
    
    // [HÀM 1] addToCart: Thêm sản phẩm vào giỏ hàng
    public void addToCart(int productId, int quantity, String size, String color, String username) {
        try {
            long customerId = getCustomerIdByUsername(username);
            if (customerId == -1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "addToCart: Không tìm thấy KhachHang cho username: " + username);
                return;
            }
            
            long cartId = getOrCreateCartId(customerId);
            if (cartId == -1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "addToCart: Không thể lấy/tạo GioHang cho maKH: " + customerId);
                return;
            }

            conn = DBContext.getConnection();
            String checkQuery = "SELECT maCTGH, soLuong FROM ChiTietGioHang " +
                                 "WHERE maGH = ? AND maSP = ? AND kichCo = ? AND mauSac = ?";
            ps = conn.prepareStatement(checkQuery);
            ps.setLong(1, cartId);
            ps.setInt(2, productId);
            ps.setString(3, size);
            ps.setString(4, color);
            rs = ps.executeQuery();

            if (rs.next()) {
                long cartItemId = rs.getLong("maCTGH");
                int existingQuantity = rs.getInt("soLuong");
                int newQuantity = existingQuantity + quantity;
                rs.close();
                ps.close(); 
                String updateQuery = "UPDATE ChiTietGioHang SET soLuong = ? WHERE maCTGH = ?";
                ps = conn.prepareStatement(updateQuery);
                ps.setInt(1, newQuantity);
                ps.setLong(2, cartItemId);
                ps.executeUpdate();
            } else {
                rs.close();
                ps.close(); 
                String insertQuery = "INSERT INTO ChiTietGioHang (maGH, maSP, soLuong, kichCo, mauSac) " +
                                     "VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(insertQuery);
                ps.setLong(1, cartId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setString(4, size);
                ps.setString(5, color);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong addToCart", e);
        } finally {
            closeConnections(); 
        }
    }

    // [HÀM 2] getCartItems: Lấy chi tiết giỏ hàng
    public List<CartItem> getCartItems(String username) {
        List<CartItem> list = new ArrayList<>();
        String query = "SELECT ct.*, sp.* " +
                        "FROM ChiTietGioHang ct " +
                        "JOIN SanPham sp ON ct.maSP = sp.maSP " +
                        "JOIN GioHang gh ON ct.maGH = gh.maGH " +
                        "JOIN KhachHang kh ON gh.maKH = kh.maKH " +
                        "JOIN NguoiDung nd ON kh.maND = nd.maND " +
                        "WHERE nd.tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product p = mapResultSetToProduct(rs); 
                CartItem item = new CartItem();
                item.setCartItemId(rs.getLong("maCTGH"));
                item.setCartId(rs.getLong("maGH"));
                item.setProductId(rs.getLong("maSP"));
                item.setQuantity(rs.getInt("soLuong"));
                item.setSize(rs.getString("kichCo"));
                item.setColor(rs.getString("mauSac"));
                item.setProduct(p);
                list.add(item);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong getCartItems", e);
        } finally {
            closeConnections();
        }
        return list;
    }

    // [HÀM 3] getCartSubtotal: Lấy tổng tiền tạm tính của giỏ hàng
    public long getCartSubtotal(String username) {
        long subtotal = 0;
        String query = "SELECT SUM(sp.gia * ct.soLuong) as total " +
                        "FROM ChiTietGioHang ct " +
                        "JOIN SanPham sp ON ct.maSP = sp.maSP " +
                        "JOIN GioHang gh ON ct.maGH = gh.maGH " +
                        "JOIN KhachHang kh ON gh.maKH = kh.maKH " +
                        "JOIN NguoiDung nd ON kh.maND = nd.maND " +
                        "WHERE nd.tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                subtotal = rs.getLong("total");
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong getCartSubtotal", e);
        } finally {
            closeConnections();
        }
        return subtotal;
    }

    // [HÀM 4] updateCartItemQuantity
    public void updateCartItemQuantity(long cartItemId, int quantity, String username) {
        String query = "UPDATE ChiTietGioHang ct " +
                        "JOIN GioHang gh ON ct.maGH = gh.maGH " +
                        "JOIN KhachHang kh ON gh.maKH = kh.maKH " +
                        "JOIN NguoiDung nd ON kh.maND = nd.maND " +
                        "SET ct.soLuong = ? " +
                        "WHERE ct.maCTGH = ? AND nd.tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, quantity);
            ps.setLong(2, cartItemId);
            ps.setString(3, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong updateCartItemQuantity", e);
        } finally {
            closeConnections();
        }
    }

    // [HÀM 5] removeCartItem
    public void removeCartItem(long cartItemId, String username) {
        String query = "DELETE ct " +
                        "FROM ChiTietGioHang ct " +
                        "JOIN GioHang gh ON ct.maGH = gh.maGH " +
                        "JOIN KhachHang kh ON gh.maKH = kh.maKH " +
                        "JOIN NguoiDung nd ON kh.maND = nd.maND " +
                        "WHERE ct.maCTGH = ? AND nd.tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, cartItemId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong removeCartItem", e);
        } finally {
            closeConnections();
        }
    }
    
    // Hàm xóa toàn bộ giỏ hàng (Hỗ trợ khi thanh toán xong)
    public void clearCart(String username) {
        // [Cần triển khai logic xóa giỏ hàng]
    }

    /** Nhóm ĐĂNG KÝ / ĐĂNG NHẬP */
    
    // Đăng nhập
    public Account getAccount(String username, String password) {
        String query = "SELECT nd.*, kh.maKH, kh.diemTichLuy FROM NguoiDung nd "
                     + "LEFT JOIN KhachHang kh ON nd.maND = kh.maND "
                     + "WHERE nd.tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("matKhau");
                if (password.equals(storedPassword)) { 
                    return mapResultSetToAccount(rs); 
                }
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return null; 
    }

    // Kiểm tra tài khoản tồn tại
    public boolean checkAccountExists(String username) {
        String query = "SELECT COUNT(*) FROM NguoiDung WHERE tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return false;
    }

    // Đăng ký
    public void signUp(String username, String fullName, String password, String phone, String address) {
        String insertNguoiDung = "INSERT INTO NguoiDung (tenDangNhap, matKhau, ten, soDienThoai, diaChi, vaiTro) "
                               + "VALUES (?, ?, ?, ?, ?, 'USER')";
        String insertKhachHang = "INSERT INTO KhachHang (maND, diemTichLuy) VALUES (?, 0)";
        
        ResultSet rsGeneratedKeys = null;
        long generatedUserId = -1;

        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false); 

            // Bước 1: Insert vào NguoiDung và lấy maND
            ps = conn.prepareStatement(insertNguoiDung, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password); 
            ps.setString(3, fullName);
            ps.setString(4, phone);
            ps.setString(5, address);
            
            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                rsGeneratedKeys = ps.getGeneratedKeys();
                if (rsGeneratedKeys.next()) {
                    generatedUserId = rsGeneratedKeys.getLong(1);
                }
            }

            if (generatedUserId == -1) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Không tạo được NguoiDung, rollback...");
                conn.rollback();
                return;
            }

            // Bước 2: Insert vào KhachHang
            ps.close(); 
            ps = conn.prepareStatement(insertKhachHang);
            ps.setLong(1, generatedUserId);
            ps.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL, rollback...", e);
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Rollback thất bại", ex);
            }
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException ex) {
                 Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rsGeneratedKeys != null) rsGeneratedKeys.close();
            } catch (SQLException e) { /* Bỏ qua */ }
            closeConnections(); 
        }
    }
    
    // Đổi mật khẩu
    public boolean checkOldPassword(long userId, String oldPassword) {
        String query = "SELECT matKhau FROM NguoiDung WHERE maND = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("matKhau");
                return oldPassword.equals(storedPassword); 
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return false;
    }

    public void changePassword(long userId, String newPassword) {
        String query = "UPDATE NguoiDung SET matKhau = ? WHERE maND = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, newPassword);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
    }
    
    // Cập nhật thông tin tài khoản
    public void updateAccountInfo(long userId, String fullName, String phone, String address) {
         // [Cần triển khai logic update]
    }
    
    // ======================================================================
    // === 2. NGUYỄN HỮU BẰNG (Admin: QL User, QL Đơn hàng (Thêm, Sửa, Xóa), Thuyết trình)
    // ======================================================================
    
    /** Nhóm QUẢN LÝ USER */
    // [Các hàm quản lý User: getAllUsers, updateUserRole, deleteUser, v.v. cần được thêm vào đây]
    
    /** Nhóm QUẢN LÝ ĐƠN HÀNG (CRUD) */
    
    // [Các hàm quản lý Order (Admin): getAllOrders, updateOrderStatus, getOrderDetailsByAdmin, v.v. cần được thêm vào đây]
    
    // 3. HÀM MAP DỮ LIỆU Đơn hàng (Cần cho các hàm QL Đơn hàng)
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getLong("maDH"));
        o.setCustomerId(rs.getLong("maKH"));
        o.setTotalAmount(rs.getLong("tongTien"));
        o.setShippingFee(rs.getLong("phiShip"));
        o.setStatus(rs.getString("trangThai"));
        o.setPaymentMethod(rs.getString("phuongThucThanhToan"));
        o.setOrderDate(rs.getTimestamp("ngayTao"));
        
        o.setShipName(rs.getString("hoTen"));
        o.setShipPhone(rs.getString("soDienThoai"));
        o.setShipAddress(rs.getString("diaChi"));
        
        return o;
    }

    // ======================================================================
    // === 3. NGUYỄN TRƯỜNG GIANG (Thiết kế CSDL, Sản phẩm, Trang cá nhân, Slide)
    // ======================================================================
    
    /** Nhóm SẢN PHẨM (User View - Lấy dữ liệu tổng quan) */

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() { return filterProducts(null, 0, 0); }
    
    // Lấy sản phẩm theo tìm kiếm/lọc
    public List<Product> filterProducts(String keyword, long categoryId, long brandId) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM SanPham WHERE 1=1";
        List<Object> params = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            query += " AND ten LIKE ?";
            params.add("%" + keyword + "%");
        }
        if (categoryId > 0) {
            query += " AND maDM = ?";
            params.add(categoryId);
        }
        if (brandId > 0) {
            query += " AND maTH = ?";
            params.add(brandId);
        }
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return list;
    }
    
    public List<Product> getProductsByCategory(String categoryName) { /*... (giữ nguyên) ...*/ return new ArrayList<>(); }
    public List<Product> getRelatedProducts(Product product) { /*... (giữ nguyên) ...*/ return new ArrayList<>(); }
    
    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM DanhMuc";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getLong("maDM"));
                c.setName(rs.getString("ten"));
                c.setImage(rs.getString("hinhAnh"));
                list.add(c);
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return list;
    }
    
    // Lấy tất cả thương hiệu
    public List<Brand> getAllBrands() {
        List<Brand> list = new ArrayList<>();
        String query = "SELECT * FROM ThuongHieu";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Brand b = new Brand();
                b.setId(rs.getLong("maTH"));
                b.setName(rs.getString("ten"));
                b.setLogo(rs.getString("logo"));
                list.add(b);
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return list;
    }
    
    /** Nhóm TRANG CÁ NHÂN (Xem đơn hàng của user) */
    
    // Lấy đơn hàng theo Username (để hiển thị trên Trang cá nhân)
    public List<Order> getOrdersByUsername(String username) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT dh.* FROM DonHang dh " +
                        "JOIN KhachHang kh ON dh.maKH = kh.maKH " +
                        "JOIN NguoiDung nd ON kh.maND = nd.maND " +
                        "WHERE nd.tenDangNhap = ? ORDER BY dh.ngayTao DESC";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToOrder(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }
    
    // Lấy thông tin 1 đơn hàng theo ID
    public Order getOrderById(long orderId) {
        String query = "SELECT * FROM DonHang WHERE maDH = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return null;
    }
    
    // Lấy chi tiết sản phẩm trong đơn hàng
    public List<CartItem> getOrderDetails(long orderId) {
        List<CartItem> list = new ArrayList<>();
        String query = """
                        SELECT c.*, p.ten, p.hinhAnh, p.maSP
                        FROM ChiTietDonHang c
                        JOIN SanPham p ON c.maSP = p.maSP
                        WHERE c.maDH = ?
                        """;
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setQuantity(rs.getInt("soLuong"));
                item.setSize(rs.getString("kichCo"));
                item.setColor(rs.getString("mauSac"));
                
                Product p = new Product();
                p.setId(rs.getLong("maSP"));
                p.setName(rs.getString("ten"));
                p.setImage(rs.getString("hinhAnh"));
                p.setPrice(rs.getDouble("donGia"));
                
                item.setProduct(p);
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }
    
    // Xóa đơn hàng (chỉ khi trạng thái là 'Chờ xác nhận')
    public boolean deleteOrder(String orderId) {
        String query = "DELETE FROM DonHang WHERE maDH = ? AND trangThai = 'CHO_XAC_NHAN'";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, orderId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi xóa đơn hàng", e);
            return false;
        } finally {
            closeConnections();
        }
    }

    // ======================================================================
    // === 4. LÊ THIÊN HÙNG (Thanh toán, Bảng bổ sung (CSDL), Thêm ĐH vào QL admin & user)
    // ======================================================================
    
    /** Nhóm THANH TOÁN / TẠO ĐƠN HÀNG */
    
    // HÀM TẠO ĐƠN HÀNG
    public boolean createOrder(Account acc, List<CartItem> items, long totalAmount, long shippingFee, String paymentMethod) {
        String insertOrderSQL = "INSERT INTO DonHang (maKH, hoTen, email, soDienThoai, diaChi, tongTien, phiShip, phuongThucThanhToan, trangThai, ngayTao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        String insertDetailSQL = "INSERT INTO ChiTietDonHang (maDH, maSP, soLuong, donGia, thanhTien, kichCo, mauSac) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            conn = DBContext.getConnection();
            conn.setAutoCommit(false);
            
            // Insert DonHang
            ps = conn.prepareStatement(insertOrderSQL, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, acc.getCustomerId()); 
            ps.setString(2, acc.getFullName()); 
            ps.setString(3, acc.getEmail()); 
            ps.setString(4, acc.getPhone()); 
            ps.setString(5, acc.getAddress()); 
            ps.setDouble(6, (double) totalAmount); 
            ps.setDouble(7, (double) shippingFee); 
            ps.setString(8, paymentMethod); 
            ps.setString(9, "CHO_XAC_NHAN"); 
            
            int affected = ps.executeUpdate();
            long orderId = 0;
            if (affected > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) orderId = rs.getLong(1);
            }
            
            if (orderId == 0) {
                conn.rollback();
                return false;
            }
            
            // Insert ChiTietDonHang
            ps = conn.prepareStatement(insertDetailSQL);
            for (CartItem item : items) {
                ps.setLong(1, orderId);
                ps.setLong(2, item.getProductId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getProduct().getPrice());
                ps.setDouble(5, item.getProduct().getPrice() * item.getQuantity());
                ps.setString(6, item.getSize());
                ps.setString(7, item.getColor());
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            return true;
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            closeConnections();
        }
    }
    
    /** Nhóm ĐÁNH GIÁ SẢN PHẨM (Liên quan đến việc hoàn thành đơn hàng/trải nghiệm) */
    
    // Kiểm tra xem User đã mua sản phẩm và đơn hàng đã "Hoàn thành" chưa
    public boolean checkUserBoughtProduct(String username, long productId) {
        System.out.println("DEBUG: Checking review permission for User: " + username + " - Product: " + productId);
        
        String query = """
                        SELECT dh.trangThai 
                        FROM DonHang dh
                        JOIN ChiTietDonHang ctdh ON dh.maDH = ctdh.maDH
                        JOIN KhachHang kh ON dh.maKH = kh.maKH
                        JOIN NguoiDung nd ON kh.maND = nd.maND
                        WHERE nd.tenDangNhap = ? 
                          AND ctdh.maSP = ?
                        """;
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setLong(2, productId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                String status = rs.getString("trangThai");
                System.out.println("DEBUG: Found Order Status: " + status);
                
                if (status != null && (
                        status.toLowerCase().contains("giao") || 
                        status.equalsIgnoreCase("SHIPPED") ||
                        status.equalsIgnoreCase("Hoàn thành") ||
                        status.equalsIgnoreCase("Success")
                   )) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return false;
    }
    
    // Thêm đánh giá mới
    public void addReview(long userId, long productId, int star, String comment) {
        String query = "INSERT INTO DanhGia (maND, maSP, sao, noiDung, ngayTao) VALUES (?, ?, ?, ?, NOW())";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, userId);
            ps.setLong(2, productId);
            ps.setInt(3, star);
            ps.setString(4, comment);
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL addReview", e);
        } finally {
            closeConnections();
        }
    }

    // Lấy danh sách đánh giá theo sản phẩm
    public List<Review> getReviewsByProduct(long productId) {
        List<Review> list = new ArrayList<>();
        String query = 
            "SELECT dg.*, nd.ten AS tenNguoiDung " +
            "FROM DanhGia dg " +
            "JOIN NguoiDung nd ON dg.maND = nd.maND " +
            "WHERE dg.maSP = ? ORDER BY dg.ngayTao DESC";

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, productId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getLong("maDG"));
                r.setUserId(rs.getLong("maND"));
                r.setProductId(rs.getLong("maSP"));
                r.setStar(rs.getInt("sao"));
                r.setComment(rs.getString("noiDung"));
                r.setCreatedDate(rs.getTimestamp("ngayTao"));
                r.setUserName(rs.getString("tenNguoiDung"));
                list.add(r);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL getReviewsByProduct", e);
        } finally {
            closeConnections();
        }

        return list;
    }

    // Lấy trung bình số sao
    public double getAverageStars(long productId) {
        String query = "SELECT AVG(sao) FROM DanhGia WHERE maSP = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL getAverageStars", e);
        } finally {
            closeConnections();
        }
        return 0;
    }

    // ======================================================================
    // === 5. BÙI THÁI SƠN (Admin: QL Sản phẩm (Thêm, Sửa, Xóa))
    // ======================================================================

    /** Nhóm QUẢN LÝ SẢN PHẨM (CRUD cho Admin) */

    // 1. XÓA sản phẩm
    public void deleteProduct(String pid) {
        String query = "DELETE FROM SanPham WHERE maSP = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, pid);
            ps.executeUpdate();
        } catch (Exception e) {}
    }

    // 2. THÊM MỚI sản phẩm
    public void insertProduct(String name, String image, String price, String description, String category, String stock, String sizes, String colors, String material) {
        String query = "INSERT INTO SanPham (ten, hinhAnh, gia, moTa, maDM, soLuong, kichCo, mauSac, chatLieu, daBan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, price);
            ps.setString(4, description);
            ps.setString(5, category);
            ps.setString(6, stock);
            
            ps.setString(7, sizes != null ? sizes : "");
            ps.setString(8, colors != null ? colors : "");
            ps.setString(9, material != null ? material : "");
            
            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi insertProduct", e);
        } finally {
            closeConnections();
        }
    }

    // 3. LẤY 1 SẢN PHẨM (Để hiện lên form sửa)
    public Product getProductByID(String id) {
        String query = "SELECT * FROM SanPham WHERE maSP = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        } catch (Exception e) {}
        return null;
    }

    // 4. CẬP NHẬT (SỬA) sản phẩm
    public void updateProduct(String id, String name, String image, String price, String description, String category, String stock, String sold, String sizes, String colors, String material) {
        String query = "UPDATE SanPham SET ten=?, hinhAnh=?, gia=?, moTa=?, maDM=?, soLuong=?, daBan=?, kichCo=?, mauSac=?, chatLieu=? WHERE maSP=?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setString(3, price);
            ps.setString(4, description);
            ps.setString(5, category);
            ps.setString(6, stock);
            ps.setString(7, sold);
            
            ps.setString(8, sizes != null ? sizes : "");
            ps.setString(9, colors != null ? colors : "");
            ps.setString(10, material != null ? material : "");
            
            ps.setString(11, id); // WHERE maSP = ?

            ps.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi updateProduct", e);
        } finally {
            closeConnections();
        }
    }
    
    /** Nhóm PHÂN TRANG (Hỗ trợ QL Sản phẩm) */
    
    // Đếm tổng số sản phẩm (cho phân trang)
    public int countProducts(String keyword, long categoryId, long brandId) {
        String query = "SELECT COUNT(*) FROM SanPham WHERE 1=1";
        List<Object> params = new ArrayList<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            query += " AND ten LIKE ?";
            params.add("%" + keyword + "%");
        }
        if (categoryId > 0) {
            query += " AND maDM = ?";
            params.add(categoryId);
        }
        if (brandId > 0) {
            query += " AND maTH = ?";
            params.add(brandId);
        }
        
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        } catch (SQLException e) { 
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); 
        } finally { 
            closeConnections(); 
        }
        return 0;
    }

    // Lấy sản phẩm có phân trang (cho trang Danh sách sản phẩm)
    public List<Product> getPaginatedProducts(String keyword, long categoryId, long brandId, String sort, int pageIndex, int pageSize) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM SanPham WHERE 1=1";
        List<Object> params = new ArrayList<>();

        // -------------------- FILTER --------------------
        if (keyword != null && !keyword.trim().isEmpty()) {
            query += " AND ten LIKE ?";
            params.add("%" + keyword + "%");
        }
        if (categoryId > 0) {
            query += " AND maDM = ?";
            params.add(categoryId);
        }
        if (brandId > 0) {
            query += " AND maTH = ?";
            params.add(brandId);
        }

        // -------------------- SORT --------------------
        if (sort != null) {
            if (sort.equals("price_asc")) {
                query += " ORDER BY gia ASC ";
            } else if (sort.equals("price_desc")) {
                query += " ORDER BY gia DESC ";
            }
        }

        // -------------------- PAGINATION --------------------
        query += " LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add((pageIndex - 1) * pageSize);

        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);

            // set parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeConnections();
        }
        return list;
    }


    // ======================================================================
    // === 6. CÁC HÀM HỖ TRỢ NỘI BỘ ===
    // ======================================================================

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); }
    }
    
    // Ánh xạ ResultSet sang Product Entity
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
    
    // Ánh xạ ResultSet sang Account Entity
    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account acc = new Account();
        acc.setUserId(rs.getLong("maND"));
        acc.setUsername(rs.getString("tenDangNhap"));
        acc.setPassword(rs.getString("matKhau"));
        acc.setFullName(rs.getString("ten"));
        acc.setDob(rs.getDate("ngaySinh"));
        acc.setGender(rs.getString("gioiTinh"));
        acc.setAddress(rs.getString("diaChi"));
        acc.setPhone(rs.getString("soDienThoai"));
        acc.setCreatedDate(rs.getTimestamp("ngayTao"));
        acc.setRole(rs.getString("vaiTro"));
        acc.setStatus(rs.getString("trangThai"));

        // Thông tin KhachHang (JOIN)
        acc.setCustomerId(rs.getLong("maKH"));
        acc.setPoints(rs.getInt("diemTichLuy"));
        
        return acc;
    }
    
    // Lấy maKH từ username (Hỗ trợ cho Giỏ hàng)
    private long getCustomerIdByUsername(String username) throws SQLException {
        String query = "SELECT kh.maKH FROM KhachHang kh " +
                        "JOIN NguoiDung nd ON kh.maND = nd.maND " +
                        "WHERE nd.tenDangNhap = ?";
        long customerId = -1;
        try (Connection tempConn = DBContext.getConnection();
             PreparedStatement tempPs = tempConn.prepareStatement(query)) {
            tempPs.setString(1, username);
            try (ResultSet tempRs = tempPs.executeQuery()) {
                if (tempRs.next()) {
                    customerId = tempRs.getLong("maKH");
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy CustomerId", e);
            throw e; 
        }
        return customerId;
    }

    // Lấy hoặc tạo maGH (Hỗ trợ cho Giỏ hàng)
    private long getOrCreateCartId(long customerId) throws SQLException {
        long cartId = -1;
        String findCartQuery = "SELECT maGH FROM GioHang WHERE maKH = ?";
        
        try (Connection tempConn = DBContext.getConnection()) {
            // 1. Tìm giỏ hàng
            try (PreparedStatement tempPs = tempConn.prepareStatement(findCartQuery)) {
                tempPs.setLong(1, customerId);
                try (ResultSet tempRs = tempPs.executeQuery()) {
                    if (tempRs.next()) {
                        cartId = tempRs.getLong("maGH");
                    }
                }
            }
            
            // 2. Nếu không có, tạo giỏ hàng mới
            if (cartId == -1) {
                String createCartQuery = "INSERT INTO GioHang (maKH) VALUES (?)";
                try (PreparedStatement tempPs = tempConn.prepareStatement(createCartQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    tempPs.setLong(1, customerId);
                    tempPs.executeUpdate();
                    
                    try (ResultSet tempRs = tempPs.getGeneratedKeys()) {
                        if (tempRs.next()) {
                            cartId = tempRs.getLong(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy/tạo CartId", e);
            throw e; 
        }
        return cartId;
    }
}