package com.mycompany.javaweb.entity;

/**
 * FILE NÀY THAY THẾ HOÀN TOÀN FILE CŨ.
 * Đây là một POJO (Plain Old Java Object) đại diện cho một mục trong giỏ hàng.
 * Nó KHÔNG kế thừa Product, mà nó CHỨA một Product.
 * Điều này sửa tất cả các lỗi "cannot find symbol" trong DAO.
 */
public class CartItem {

    private long cartItemId; // maCTGH
    private long cartId;     // maGH
    private long productId;  // maSP
    private int quantity;
    private String size;
    private String color;
    
    // Đối tượng này sẽ được điền khi JOIN với bảng SanPham
    private Product product;

    // Phải có constructor rỗng để DAO hoạt động
    public CartItem() {
    }

    // Getters and Setters (Bắt buộc)
    
    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}