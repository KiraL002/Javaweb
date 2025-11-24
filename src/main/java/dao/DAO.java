package com.mycompany.javaweb.dao;

import com.mycompany.javaweb.context.DBContext;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Brand;
import com.mycompany.javaweb.entity.CartItem;
import com.mycompany.javaweb.entity.Category;
import com.mycompany.javaweb.entity.Order;
import com.mycompany.javaweb.entity.Product;
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

    // --- CÁC HÀM SẢN PHẨM/DANH MỤC/THƯƠNG HIỆU (Giữ nguyên) ---
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
    public List<Product> getAllProducts() { return filterProducts(null, 0, 0); }
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
    // ... (Các hàm product/category/brand khác giữ nguyên) ...
    public List<Product> getNewProducts() { /*... (giữ nguyên) ...*/ return new ArrayList<>(); }
    public List<Product> getBestSellerProducts() { /*... (giữ nguyên) ...*/ return new ArrayList<>(); }
    public List<Product> getProductsByCategory(String categoryName) { /*... (giữ nguyên) ...*/ return new ArrayList<>(); }
    public List<Product> getRelatedProducts(Product product) { /*... (giữ nguyên) ...*/ return new ArrayList<>(); }
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
    
    // ==========================================================
    // === HÀM MỚI 1: ĐẾM SẢN PHẨM (CHO PHÂN TRANG) ===
    // ==========================================================
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
                return rs.getInt(1); // Trả về số lượng
            }
        } catch (SQLException e) { 
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); 
        } finally { 
            closeConnections(); 
        }
        return 0; // Trả về 0 nếu có lỗi
    }

    // ==========================================================
    // === HÀM MỚI 2: LẤY SẢN PHẨM CÓ PHÂN TRANG ===
    // ==========================================================
    public List<Product> getPaginatedProducts(String keyword, long categoryId, long brandId, int pageIndex, int pageSize) {
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
        
        // Thêm LIMIT (số lượng) và OFFSET (vị trí bắt đầu)
        query += " ORDER BY maSP ASC LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add((pageIndex - 1) * pageSize);
        
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
        } catch (SQLException e) { 
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); 
        } finally { 
            closeConnections(); 
        }
        return list;
    }
    
    
    // --- CÁC HÀM ACCOUNT (Giữ nguyên) ---
    
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
                String storedPassword = rs.getString("matKhau"); // Lấy mật khẩu thuần từ DB
                if (password.equals(storedPassword)) { // So sánh mật khẩu thuần
                    return mapResultSetToAccount(rs); 
                }
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return null; // Sai tên đăng nhập hoặc mật khẩu
    }


    public boolean checkAccountExists(String username) {
        String query = "SELECT COUNT(*) FROM NguoiDung WHERE tenDangNhap = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu đếm > 0
            }
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
        return false;
    }

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
            ps.setString(2, password); // Lưu mật khẩu thuần
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
                return; // Dừng lại
            }

            // Bước 2: Insert vào KhachHang
            ps.close(); // Đóng PreparedStatement cũ
            ps = conn.prepareStatement(insertKhachHang);
            ps.setLong(1, generatedUserId); // Dùng maND vừa lấy
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

    public boolean checkOldPassword(long userId, String oldPassword) {
        String query = "SELECT matKhau FROM NguoiDung WHERE maND = ?";
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("matKhau");
                return oldPassword.equals(storedPassword); // So sánh mật khẩu thuần
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
            ps.setString(1, newPassword); // Lưu mật khẩu thuần mới
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); } 
        finally { closeConnections(); }
    }
    public void updateAccountInfo(long userId, String fullName, String phone, String address) {
        //Chua lam
    }

    // --- (Các hàm còn lại giữ nguyên) ---
    public List<Order> getOrdersByUserEmail(String userEmail) { return new ArrayList<>(); }
    public void clearCart(String userEmail) { }
    public void createOrder(Order order) { }

    
    // ======================================================================
    // === CÁC HÀM GIỎ HÀNG (Giữ nguyên) ===
    // ======================================================================
    
    // [PHƯƠNG THỨC HỖ TRỢ 1]
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
            throw e; // Ném lỗi ra ngoài
        }
        return customerId;
    }

    // [PHƯƠNG THỨC HỖ TRỢ 2]
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
            throw e; // Ném lỗi ra ngoài
        }
        return cartId;
    }

    // [HÀM 1] addToCart
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

    // [HÀM 2] getCartItems
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

    // [HÀM 3] getCartSubtotal
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

    // [HÀM 4] updateCartItemQuantity (Đã sửa cho MySQL)
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

    // [HÀM 5] removeCartItem (Đã sửa cho MySQL)
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
    
    // --- CÁC HÀM NỘI BỘ (Giữ nguyên) ---
    
    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); }
    }
    
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

        acc.setCustomerId(rs.getLong("maKH"));
        acc.setPoints(rs.getInt("diemTichLuy"));
        
        return acc;
    }

    // ======================================================================
    // === CÁC HÀM ĐƠN HÀM
    // ======================================================================

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
            Order o = new Order();
            o.setOrderNumber(String.valueOf(rs.getLong("maDH"))); // chuyển BIGINT sang String
            o.setUserEmail(rs.getString("email"));

            // Nếu bạn có bảng chi tiết giỏ hàng, items có thể load sau
            o.setItems(null); // hoặc gọi hàm loadCartItems(maDH)

            o.setSubtotal(rs.getLong("tongTien")); // tạm set subtotal = total nếu chưa có shipping
            o.setShipping(0); // nếu chưa có thông tin shipping
            o.setTotal(rs.getLong("tongTien")); // total = tongTien trong DB

            o.setCreatedDate(rs.getTimestamp("ngayTao")); // Timestamp -> java.util.Date
            o.setStatus(rs.getString("trangThai"));

            o.setShippingAddress(rs.getString("diaChi"));
            o.setPhone(rs.getString("soDienThoai"));
            o.setPaymentMethod(rs.getString("phuongThucThanhToan"));

            return o;
        }


}