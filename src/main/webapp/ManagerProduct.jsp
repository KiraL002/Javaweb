<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Quản lý Sản Phẩm - Admin</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        body { color: #566787; background: #f5f5f5; font-family: 'Varela Round', sans-serif; font-size: 13px; }
        .table-responsive { margin: 30px 0; }
        .table-wrapper { background: #fff; padding: 20px 25px; border-radius: 3px; box-shadow: 0 1px 1px rgba(0,0,0,.05); }
        .table-title { padding-bottom: 15px; background: #435d7d; color: #fff; padding: 16px 30px; margin: -20px -25px 10px; border-radius: 3px 3px 0 0; }
        .table-title h2 { margin: 5px 0 0; font-size: 24px; }
        .btn-success { background-color: #10c469; }
        img { width: 60px; height: 60px; object-fit: cover; border-radius: 4px; }
        /* Modal styles */
        .modal .modal-dialog { max-width: 400px; }
        .modal .modal-header, .modal .modal-body, .modal .modal-footer { padding: 20px 30px; }
        .modal .modal-content { border-radius: 3px; }
        .modal .modal-footer { background: #ecf0f1; border-radius: 0 0 3px 3px; }
        .modal .modal-title { display: inline-block; }
        .modal .form-control { border-radius: 2px; box-shadow: none; border-color: #dddddd; }
        .modal textarea.form-control { resize: vertical; }
        .modal .btn { border-radius: 2px; min-width: 100px; }
        .modal form label { font-weight: normal; }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="table-responsive">
            <div class="table-wrapper">
                <div class="table-title">
                    <div class="row">
                        <div class="col-sm-6">
                            <h2>Quản lý <b>Sản Phẩm</b></h2>
                        </div>
                        <div class="col-sm-6 text-right">
                            <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Thêm mới</span></a>
                            <a href="home" class="btn btn-primary"><i class="material-icons">&#xE88A;</i> <span>Về trang chủ</span></a>
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Giá tiền</th>
                            <th>Kho</th>
                            <th>Đã bán</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listP}" var="o">
                            <tr>
                                <td>${o.id}</td>
                                <td>
                                    <img src="${o.displayImage}" alt="img">
                                </td>
                                <td>${o.name}</td>
                                <td>
                                    ${o.displayPriceFormatted}
                                </td>
                                <td>${o.stock}</td>
                                <td>${o.sold}</td>
                                <td>
                                    <a href="loadProduct?pid=${o.id}" class="edit" data-toggle="tooltip" title="Edit"><i class="material-icons" style="color: #FFC107;">&#xE254;</i></a>
                                    <a href="delete?pid=${o.id}" class="delete" data-toggle="tooltip" title="Delete" onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');"><i class="material-icons" style="color: #F44336;">&#xE872;</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="addEmployeeModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="add" method="post">
                    <div class="modal-header">
                        <h4 class="modal-title">Thêm Sản Phẩm Mới</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Tên sản phẩm</label>
                            <input name="name" type="text" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Hình ảnh (Link URL)</label>
                            <input name="image" type="text" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Giá tiền</label>
                            <input name="price" type="number" class="form-control" required>
                        </div>
                        <div class="form-group">
                            <label>Tiêu đề (Mô tả ngắn)</label>
                            <textarea name="title" class="form-control" required></textarea>
                        </div>
                        <div class="form-group">
                            <label>Mô tả chi tiết</label>
                            <textarea name="description" class="form-control"></textarea>
                        </div>
                        <div class="form-group">
                            <label>Danh mục</label>
                            <select name="category" class="form-control">
                                <option value="1">Áo Thun</option>
                                <option value="2">Quần Jeans</option>
                                <option value="3">Áo Khoác</option>
                                <option value="4">Đầm/Váy</option>
                                <option value="5">Áo Sơ Mi</option>
                                <option value="6">Quần Short</option>
                                <option value="7">Áo Len</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Số lượng kho</label>
                            <input name="stock" type="number" class="form-control" value="100">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input type="button" class="btn btn-default" data-dismiss="modal" value="Hủy">
                        <input type="submit" class="btn btn-success" value="Thêm ngay">
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

</body>
</html>