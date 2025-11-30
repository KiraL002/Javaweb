package com.mycompany.javaweb.entity;

import java.sql.Timestamp;
import java.util.Date; // Import thêm Date để hỗ trợ constructor cũ
import java.util.List;

public class Order {
    
    private long orderId;       // maDH
    private long customerId;    // maKH
    private Timestamp orderDate;    // ngayDat (hoặc ngayTao)
    private long totalAmount;   // tongTien
    private long shippingFee;   // phiShip
    private String status;      // trangThai
    private String paymentMethod; // phuongThucTT
    
    private String shipName;    // tenGiao
    private String shipPhone;   // sdtGiao
    private String shipAddress; // diaChiGiao
    
    // Thuộc tính hỗ trợ
    private String userEmail; 
    private String orderNumber; 
    private List<CartItem> items; 
    private long subtotal;      
    private long total;         
    private String userName;    
    
    // Thuộc tính alias (để sửa lỗi setUserId)
    private long userId; // Alias cho customerId

    // Constructor rỗng (Bắt buộc)
    public Order() {
    }

    // --- CONSTRUCTOR CHO CÁC FILE CŨ (VNPay, StockDAO...) ---
    // Thứ tự tham số dựa trên log lỗi của bạn:
    // String, String, List<CartItem>, long, long, long, Date/Timestamp, String, String, String, String
    public Order(String orderNumber, String userEmail, List<CartItem> items, long subtotal, long shipping, long total, Date orderDate, String status, String shippingAddress, String phone, String paymentMethod) {
        this.orderNumber = orderNumber;
        this.userEmail = userEmail;
        this.items = items;
        this.subtotal = subtotal;
        this.shippingFee = shipping; // shipping -> shippingFee
        this.totalAmount = total;    // total -> totalAmount
        this.orderDate = new Timestamp(orderDate.getTime()); // Date -> Timestamp
        this.status = status;
        this.shipAddress = shippingAddress; // shippingAddress -> shipAddress
        this.shipPhone = phone; // phone -> shipPhone
        this.paymentMethod = paymentMethod;
    }

    // --- Getters và Setters ---

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public long getCustomerId() { return customerId; }
    public void setCustomerId(long customerId) { 
        this.customerId = customerId; 
        this.userId = customerId; // Đồng bộ userId
    }

    // Alias userId cho AdminDAO
    public long getUserId() { return userId; }
    public void setUserId(long userId) { 
        this.userId = userId;
        this.customerId = userId; // Đồng bộ customerId
    }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(long totalAmount) { this.totalAmount = totalAmount; }

    public long getShippingFee() { return shippingFee; }
    public void setShippingFee(long shippingFee) { this.shippingFee = shippingFee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getShipName() { return shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public String getShipPhone() { return shipPhone; }
    public void setShipPhone(String shipPhone) { this.shipPhone = shipPhone; }

    public String getShipAddress() { return shipAddress; }
    public void setShipAddress(String shipAddress) { this.shipAddress = shipAddress; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getOrderNumber() { 
        if (orderNumber == null) return String.valueOf(orderId);
        return orderNumber; 
    }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public long getSubtotal() { return subtotal; }
    public void setSubtotal(long subtotal) { this.subtotal = subtotal; }

    // Alias Shipping
    public long getShipping() { return shippingFee; }
    public void setShipping(long shipping) { this.shippingFee = shipping; }

    // Alias Total
    public long getTotal() { return totalAmount; }
    public void setTotal(long total) { this.totalAmount = total; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    // Alias cho JSP cũ
    public Timestamp getCreatedDate() { return orderDate; }
    public void setCreatedDate(Timestamp createdDate) { this.orderDate = createdDate; }
    
    public String getShippingAddress() { return shipAddress; }
    public void setShippingAddress(String address) { this.shipAddress = address; }
    
    public String getPhone() { return shipPhone; }
    public void setPhone(String phone) { this.shipPhone = phone; }
}