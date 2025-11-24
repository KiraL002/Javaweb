package com.mycompany.javaweb.entity;

import java.util.Date;
import java.util.List;

/**
 * Entity cho Đơn hàng
 * ĐÃ CẬP NHẬT: Mở rộng để khớp với JSON schema (thêm địa chỉ, SĐT, v.v.)
 */
public class Order {
    private String orderNumber; // THAY ĐỔI: từ int id -> String
    private String userEmail;
    private List<CartItem> items;
    private long subtotal;
    private long shipping;
    private long total;
    private Date createdDate;
    private String status;
    
    // CÁC TRƯỜNG MỚI TỪ JSON
    private String shippingAddress;
    private String phone;
    private String paymentMethod;

    // Hàm tạo (constructor) đã được cập nhật
    public Order(String orderNumber, String userEmail, List<CartItem> items, 
                 long subtotal, long shipping, long total, Date createdDate, 
                 String status, String shippingAddress, String phone, String paymentMethod) {
        this.orderNumber = orderNumber;
        this.userEmail = userEmail;
        this.items = items;
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.total = total;
        this.createdDate = createdDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.phone = phone;
        this.paymentMethod = paymentMethod;
    }

    // --- Getters and Setters (Bao gồm các trường mới) ---
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public long getSubtotal() { return subtotal; }
    public void setSubtotal(long subtotal) { this.subtotal = subtotal; }
    public long getShipping() { return shipping; }
    public void setShipping(long shipping) { this.shipping = shipping; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}

