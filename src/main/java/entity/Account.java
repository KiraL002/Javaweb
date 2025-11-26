package com.mycompany.javaweb.entity;

import java.util.Date;
// MỚI: Thêm import này
import java.io.Serializable;

/**
 * Entity Account (Đã cập nhật)
 * ĐÃ SỬA: Thêm "implements Serializable" để có thể lưu vào Session
 */
public class Account implements Serializable { // <-- ĐÃ THÊM
    
    // MỚI: Thêm ID cho Serializable
    private static final long serialVersionUID = 1L;
    
    // Thuộc tính từ NguoiDung
    private long userId; // maND
    private String username; // tenDangNhap
    private String password; // matKhau
    private String fullName; // ten
    private Date dob; // ngaySinh
    private String gender; // gioiTinh
    private String address; // diaChi
    private String phone; // soDienThoai
    private Date createdDate; // ngayTao
    private String role; // vaiTro ('ADMIN' hoặc 'USER')
    private String status;
    // Thuộc tính từ KhachHang
    private long customerId; // maKH
    private int points; // diemTichLuy

    // Constructor rỗng (Bắt buộc)
    public Account() {
    }
    
    // Constructor 5 tham số mà DAO (dữ liệu giả / signUp) cần
    public Account(int id, String email, String password, String fullName, boolean isAdmin) {
        this.userId = id;
        this.username = email; // Ánh xạ email -> username
        this.password = password;
        this.fullName = fullName;
        this.role = isAdmin ? "ADMIN" : "USER";
    }

    // --- Getters và Setters (Đây là phần DAO/Control đang cần) ---
    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { this.customerId = customerId; }
    
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    
    // --- Phương thức tiện ích ---
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }
    public String getInitial() {
        if (fullName != null && !fullName.isEmpty()) {
            return String.valueOf(fullName.charAt(0)).toUpperCase();
        }
        return "?";
    }
    public String getEmail() {
        // Tạm thời trả về username, vì CSDL của bạn không có trường email riêng
        return this.username; 
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}