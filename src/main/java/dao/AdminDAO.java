package com.mycompany.javaweb.dao;

import com.mycompany.javaweb.context.DBContext;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String query = "SELECT * FROM orders order by ngaytao desc";
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
        o.setOrderNumber(rs.getString("order_id")); // dùng order_id VARCHAR
        o.setUserEmail(rs.getString("email"));
        
// Nếu bạn có bảng chi tiết giỏ hàng, items có thể load sau
        o.setItems(null); // hoặc gọi hàm loadCartItems(order_id)

        o.setSubtotal(rs.getLong("subtotal")); // Tổng tiền chưa tính shipping
        o.setShipping(rs.getLong("shipping")); // Phí vận chuyển
        o.setTotal(rs.getLong("tongTien")); // Tổng tiền gồm phí vận chuyển

        o.setCreatedDate(rs.getTimestamp("ngayTao")); // Timestamp -> java.util.Date
        o.setStatus(rs.getString("trangThai"));

        o.setShippingAddress(rs.getString("diaChi"));
        o.setPhone(rs.getString("soDienThoai"));
        o.setPaymentMethod(rs.getString("phuongThucThanhToan"));

        return o;
    

    

}

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) { Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e); }
    }

}
