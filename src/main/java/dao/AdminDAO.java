package com.mycompany.javaweb.dao;


import com. mycompany.javaweb.context.DBContext;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Order;
import com.mycompany.javaweb.entity.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mycompany.javaweb.utils.StringUtils;

public class AdminDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

//   ------------------ ACCOUNT ----------------------

    public List<Account> getAllAccounts(){
        String query = "SELECT * FROM NguoiDung nd LEFT JOIN KhachHang kh ON nd.maND = kh.maND";
        List<Account>  list = new ArrayList<>();
        try {
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account account = mapResultSetToAccount(rs);
                list.add(account);
            }
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
        }
        finally { closeConnections(); }
        return  list;
    }
    public void updateAccountInfo(long userId, String userName, String phone, String role, String status) {
        String query = "UPDATE NguoiDung SET tenDangNhap = ?,soDienThoai = ?, vaiTro = ?, trangThai = ? WHERE maND = ?";
        try{
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,userName);
            ps.setString(2,phone);
            ps.setString(3,role);
            ps.setString(4,status);
            ps.setLong(5,userId);
            ps.executeUpdate();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); }
        finally { closeConnections(); }
    }

    public boolean  deleteAccount(long userId) {
        String query = "DELETE FROM NguoiDung WHERE maND = ?";
        try{
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setLong(1,userId);
            int rowsAffected  = ps.executeUpdate();
            return (rowsAffected > 0);
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        finally { closeConnections(); }
    }

//   ----------------- ORDERS -------------------

    public List<Order> getAllOrders() {
        String query = """
                       SELECT * FROM donhang dh 
                       join nguoidung nd on dh.maKH = nd.maND 
                       order by dh.ngaytao desc
                   """;
        List<Order> res = new ArrayList<>();
        try{
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order o = mapResultSetToOrder(rs);
                res.add(o);
            }
        }
        catch(SQLException e){
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong removeCartItem", e);
        } finally {
            closeConnections();
        }
        return res;
    }
    public void updateOrder(String id,String status,String paymentMethod){
        StringBuilder query = new StringBuilder("""
                       UPDATE donhang SET
                       """);
        List<String> params = new ArrayList<>();
        if(!StringUtils.isEmpty(status)){
            query.append(" trangThai = ? ");
            params.add(status);
        }
        if(!StringUtils.isEmpty(paymentMethod)){
            if(params.size()>0) query.append(",");
            query.append(" phuongThucThanhToan = ? ");
            params.add(paymentMethod);
        }
        if (params.isEmpty()) {
            return;
        }
        query.append(" WHERE maDH = ?");
        params.add(id);
        try{
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query.toString());
            for(int i=0; i<params.size(); i++){
                ps.setString(i+1,params.get(i));
            }
            ps.executeUpdate();
        }
        catch(SQLException e){
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong removeCartItem", e);
        } finally {
            closeConnections();
        }
    }
    public Order getOrderById(String id){
        String query = "SELECT * FROM donhang WHERE maDH = ?";
        Order o = new Order();
        try{
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                o = mapResultSetToOrder(rs);
        }
        catch(SQLException e){
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong removeCartItem", e);
        } finally {
            closeConnections();
        }
        return o;
    }
    public List<OrderItem> getOrderItemsByOrderId(String orderId){
        List<OrderItem> items = new ArrayList<>();
        String query = """
                       SELECT * 
                        FROM ChiTietDonHang ctdh
                        JOIN SanPham sp ON ctdh.maSP = sp.maSP
                        WHERE ctdh.maDH = ?
                       """;
        try{
            conn = DBContext.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1,orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                OrderItem item = mapResultSetToOrderItem(rs);
                items.add(item);
            }

        }
        catch(SQLException e){
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Lỗi SQL trong removeCartItem", e);
        } finally {
            closeConnections();
        }
        return items;
    }
//   ------------------- MAP ----------------------

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
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getLong("maDH"));
        o.setUserId(rs.getLong("maKH"));
        o.setUserEmail(rs.getString("email"));

        // Nếu bạn có bảng chi tiết giỏ hàng, items có thể load sau
        o.setItems(null); // hoặc gọi hàm loadCartItems(maDH)
        o.setUserName(rs.getString("hoTen"));
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


    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderItem item = new OrderItem();

        item.setOrderDetailId(rs.getLong("maCTDH"));
        item.setProductId(rs.getLong("maSP"));
        item.setQuantity(rs.getInt("soLuong"));
        item.setUnitPrice(rs.getDouble("donGia")); // Giá cố định lúc đặt hàng
        item.setSize(rs.getString("kichCo"));
        item.setColor(rs.getString("mauSac"));
        item.setProductName(rs.getString("ten"));
        item.setSubtotal(rs.getDouble("thanhTien")); // Map cột thanhTien

        return item;
    }

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); }
    }

}
