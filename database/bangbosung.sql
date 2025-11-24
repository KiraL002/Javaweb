use clothing_shop;
-- tôi thử thêm dữ liệu bào bảng có sẵn trong database nhưng ko được nên tạo thêm bảng này á 
-- bảng này chỉ khác có mỗi order_id và maDH thoi về cách tạo mấy cái khác thì hơi khác tên 1 xíu nhé
CREATE TABLE Orders (
    order_id VARCHAR(255) PRIMARY KEY,  -- order_id là khóa chính, có thể là mã đơn hàng (ví dụ: MAU123456)
    user_email VARCHAR(100),
   	maKH BIGINT,					-- Email người dùng (liên kết với tài khoản)
    maNV BIGINT,
    subtotal BIGINT,                    -- Tổng tiền hàng (chưa tính phí vận chuyển)
    shipping BIGINT,                    -- Phí vận chuyển
    total BIGINT,                       -- Tổng tiền (bao gồm phí vận chuyển)
    created_date TIMESTAMP,             -- Ngày tạo đơn hàng
    status VARCHAR(50),                 -- Trạng thái đơn hàng (ví dụ: "Chờ thanh toán", "Đã thanh toán")
    shipping_address VARCHAR(255),      -- Địa chỉ giao hàng
    phone VARCHAR(15),                  -- Số điện thoại người nhận
    payment_method VARCHAR(50),          -- Phương thức thanh toán (VNPay, COD, ...)
    FOREIGN KEY (maKH) REFERENCES KhachHang(maKH) ON DELETE CASCADE,
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV) ON DELETE SET NULL
);
CREATE TABLE OrderItems (
    order_id VARCHAR(255),               -- Mã đơn hàng (liên kết với bảng Orders)
    maSP BIGINT,                         -- Mã sản phẩm
    soLuong INT,                         -- Số lượng sản phẩm trong đơn hàng
    size VARCHAR(50),                    -- Kích thước sản phẩm
    color VARCHAR(50),                   -- Màu sắc sản phẩm
    PRIMARY KEY (order_id, maSP),        -- Đặt cặp (order_id, maSP) làm khóa chính
    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,  -- Liên kết với bảng Orders
    FOREIGN KEY (maSP) REFERENCES SanPham(maSP) ON DELETE CASCADE          -- Liên kết với bảng sản phẩm
);
