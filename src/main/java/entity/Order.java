package com.mycompany.javaweb.entity;


import java.util.Date;
import java.util.List;
import com.mycompany.javaweb.entity.CartItem;

/**
 * Entity cho Đơn hàng
 * ĐÃ CẬP NHẬT: Mở rộng để khớp với JSON schema (thêm địa chỉ, SĐT, v.v.)
 */
public class Order {
    private String orderNumber;
    private long orderId;

    //    Thông tin người đặt
    private long userId;
    private String userEmail;
    private String userName;

    //    Thông tin đơn hàng
    private List<CartItem> items;
    private long subtotal;
    private long shipping;
    private long total;
    private Date createdDate;
    private String status;




    private String shippingAddress;
    private String phone;
    private String paymentMethod;

    public Order() {}
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
    public long getOrderId() { return orderId; }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getOrderNumber() {
        return orderNumber;
    }
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

