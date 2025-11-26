<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Chỉnh sửa Sản Phẩm</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h3 class="text-center mb-4">Chỉnh sửa Sản Phẩm</h3>
                <form action="edit" method="post">
                    <input type="hidden" name="id" value="${detail.id}">

                    <div class="form-group">
                        <label>Tên sản phẩm</label>
                        <input value="${detail.name}" name="name" type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Hình ảnh (URL)</label>
                        <input value="${detail.image}" name="image" type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Giá tiền</label>
                        <input value="${detail.price}" name="price" type="number" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Mô tả</label>
                        <textarea name="description" class="form-control">${detail.description}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Danh mục (ID)</label>
                        <input value="${detail.categoryId}" name="category" type="number" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Số lượng kho</label>
                        <input value="${detail.stock}" name="stock" type="number" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Đã bán</label>
                        <input value="${detail.sold}" name="sold" type="number" class="form-control">
                    </div>

                    <button type="submit" class="btn btn-success btn-block">Lưu thay đổi</button>
                    <a href="manage-product" class="btn btn-secondary btn-block">Hủy</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>