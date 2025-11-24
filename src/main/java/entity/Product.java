package com.mycompany.javaweb.entity;


import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Entity Product (Đã cập nhật)
 * Ánh xạ bảng SanPham
 * ĐÃ SỬA: Thêm lại constructor 14 tham số (dùng cho CartItem)
 * và các phương thức setters/getters khớp với CSDL
 */
public class Product {
    
    // Khớp với CSDL
    private long id; // maSP
    private long categoryId; // maDM
    private long brandId; // maTH
    private String name; // ten
    private String description; // moTa
    private double price; // gia
    private int stock; // soLuong
    private String sizes; // kichCo (Lưu dạng chuỗi: "S,M,L")
    private String colors; // mauSac (Lưu dạng chuỗi: "Trắng,Đen")
    private String material; // chatLieu
    private String image; // hinhAnh
    private int sold; // daBan
    
    // Thuộc tính Join (từ bảng khác)
    private String categoryName;
    private String brandName;
    
    // Thuộc tính kế thừa (dùng cho CartItem)
    private List<String> images;
    private boolean isNew;
    private boolean isBestSeller;
    private String careInstructions;

    // Constructor rỗng (Bắt buộc)
    public Product() {
    }
    
    // Constructor 14 tham số mà CartItem cần
    public Product(int id, String name, String description, long price, long salePrice, List<String> images, List<String> sizes, List<String> colors, String category, int stock, boolean isNew, boolean isBestSeller, String material, String careInstructions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        // (Bỏ qua salePrice, CSDL SQL không có)
        this.images = images;
        this.sizes = (sizes != null) ? String.join(",", sizes) : "";
        this.colors = (colors != null) ? String.join(",", colors) : "";
        this.categoryName = category;
        this.stock = stock;
        this.isNew = isNew;
        this.isBestSeller = isBestSeller;
        this.material = material;
        this.careInstructions = careInstructions;
    }

    // --- Getters và Setters (Đây là phần DAO đang cần) ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public long getCategoryId() { return categoryId; }
    public void setCategoryId(long categoryId) { this.categoryId = categoryId; }
    
    public long getBrandId() { return brandId; }
    public void setBrandId(long brandId) { this.brandId = brandId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    public String getSizes() { return sizes; }
    public void setSizes(String sizes) { this.sizes = sizes; }
    
    public String getColors() { return colors; }
    public void setColors(String colors) { this.colors = colors; }
    
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    
    public int getSold() { return sold; }
    public void setSold(int sold) { this.sold = sold; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    
    // Getters cho các trường kế thừa (dùng bởi CartItem)
    public List<String> getImages() { return images; }
    public boolean isIsNew() { 
        // Logic tạm thời (vì CSDL không có)
        return this.id % 2 == 0; 
    }
    public boolean isIsBestSeller() { 
        // Logic tạm thời (dựa trên CSDL)
        return this.sold > 100;
    }
    public String getCareInstructions() { return careInstructions; }


    // --- Các phương thức tiện ích ---
    public String[] getSizeList() {
        if (sizes != null && !sizes.isEmpty()) { return sizes.split(","); }
        return new String[0];
    }
    public String[] getColorList() {
        if (colors != null && !colors.isEmpty()) { return colors.split(","); }
        return new String[0];
    }
    public String getDisplayImage() {
        if (image != null && !image.isEmpty()) { return image; }
        return "https://placehold.co/600x800/f0f0f0/ccc?text=No+Image";
    }
    public double getDisplayPrice() { return this.price; }
    public String getDisplayPriceFormatted() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return nf.format(this.price);
    }
    
    // CSDL không có giá sale
    public boolean isHasDiscount() { return false; }
    public int getDiscountPercent() { return 0; }
    public long getSalePrice() { return (long) this.price; }
}