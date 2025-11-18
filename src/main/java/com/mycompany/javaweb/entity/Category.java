package com.mycompany.javaweb.entity;

/**
 * Entity Category (Đã cập nhật)
 * Ánh xạ bảng DanhMuc
 * ĐÃ SỬA: Thêm lại constructor rỗng (no-argument constructor)
 */
public class Category {
    
    private long id; // maDM
    private String name; // ten
    private String image; // hinhAnh

    // Constructor rỗng (BẮT BUỘC - để sửa lỗi)
    public Category() {
    }
    
    // Constructor 3 tham số mà DAO (dữ liệu giả) cần (vẫn giữ lại)
    public Category(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    // --- Getters và Setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}