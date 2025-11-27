<%--
    Document   : UserManagement
    Created on : 24 thg 11, 2025, 18:52:23
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<table class="min-w-full border border-gray-200 rounded-lg overflow-hidden ">
    <thead class="bg-gray-100">
    <tr>
        <th class="px-4 py-2 text-left text-gray-700">STT</th>
        <th class="px-4 py-2 text-left text-gray-700">Tên người dùng</th>
        <!--<th class="px-4 py-2 text-left text-gray-700">Tên tài khoản</th>-->
        <th class="px-4 py-2 text-left text-gray-700">SĐT</th>
        <th class="px-4 py-2 text-left text-gray-700">Vai trò</th>
        <th class="px-4 py-2 text-left text-gray-700">Trạng thái</th>
        <th class="px-4 py-2 text-left text-gray-700">Hành động</th>
    </tr>
    </thead>
    <tbody class="bg-white divide-y divide-gray-200">
    <c:forEach var="u" items="${users}" varStatus ="status">

        <form action="admin/users" method="post">
            <input type="hidden" name="userId" value="${u.userId}" />
            <tr class="odd:bg-gray-50 even:bg-white even:hover:bg-gray-100 odd:hover:bg-gray-100">
                <td class="px-4 py-2">${status.index+1}</td>
                <!--                        <td class="px-4 py-2">
                        <span class="view">${u.fullName}</span>
                        <input class="edit hidden border px-2 py-1" type="text" name="fullname" value="${u.fullName}" />
                    </td>-->
                <td class="px-4 py-2">
                    <span class="view">${u.username}</span>
                    <input class="edit hidden border px-2 py-1" type="text" name="username" value="${u.username}" />
                </td>
                <td class="px-4 py-2">
                    <span class="view">${u.phone}</span>
                    <input class="edit hidden border px-2 py-1" type="text" name="phone" value="${u.phone}" />
                </td>
                <td class="px-4 py-2">
                    <span class="view">${u.role}</span>
                    <select class="edit hidden border px-2 py-1" name="role">
                        <option value="USER" ${u.role == "USER" ? 'selected' : ''}>USER</option>
                        <option value="ADMIN" ${u.role == "ADMIN" ? 'selected' : ''}>ADMIN</option>
                        <option value="STAFF" ${u.role == "STAFF" ? 'selected' : ''}>STAFF</option>
                    </select>
                </td>
                <td class="px-4 py-2">
                        <span class="view px-2 py-1 rounded-full text-white ${u.status == "ACTIVE" ? 'bg-green-500' : 'bg-red-500'} text-sm font-semibold" name="status">
                                ${u.status}
                        </span>
                    <select class="edit hidden border px-2 py-1" name="status">
                        <option value="ACTIVE" ${u.status == "ACTIVE" ? 'selected' : ''}>ACTIVE</option>
                        <option value="SUSPENDED" ${u.status == "SUSPENDED" ? 'selected' : ''}>SUSPENDED</option>
                    </select>
                </td>

                <td class="px-4 py-2 relative">
                        <span class="view flex space-x-2">
                            <button type="button">
                                <i class="edit-btn fa-solid fa-pen-to-square text-blue-500 hover:text-blue-700" title="Chỉnh sửa"></i>
                            </button>
                            <button type="button" class="delete-btn" data-id="${u.userId}">
                                <i class="fa-solid fa-trash text-red-500 hover:text-red-700" title="Xóa vĩnh viễn"></i>
                            </button>
                        </span>

                    <span class="edit hidden absolute top-1/2 -translate-y-1/2 left-4 flex space-x-2">
                            <button type="submit" >
                                <i class="save-btn fa-solid fa-floppy-disk text-green-500 hover:text-green-700" title="Lưu"></i>
                            </button>
                            <button type="button">
                                <i class="cancel-btn fa-solid fa-x text-black-500 hover:text-black-700" title="Hủy"></i>
                            </button>
                        </span>
                </td>

            </tr>
        </form>
    </c:forEach>
    </tbody>
</table>
</body>
<script>
    // Nút sửa
    document.querySelectorAll(".edit-btn").forEach(btn=>{
        btn.addEventListener("click",()=>{
            const tr = btn.closest("tr");
            tr.querySelectorAll(".view").forEach(v=>v.classList.add("hidden"));
            tr.querySelectorAll(".edit").forEach(v=>v.classList.remove("hidden"));
        })
    })

    // Nút lưu
    document.querySelectorAll(".save-btn").forEach(btn=>{
        btn.addEventListener("click",()=>{
            const tr = btn.closest("tr");
            // const data = {};
            // tr.querySelectorAll(".edit").forEach(input=>{
            //     data[input.name] = input.value;
            // });
            // fetch("/admin/users", {
            //     method: 'POST',
            //     headers: {'Content-Type': 'application/json'},
            //     body: JSON.stringify(data)
            // });
            // console.log('Dữ liệu gửi lên server:', data);
            tr.querySelectorAll(".view").forEach(v=>v.classList.remove("hidden"));
            tr.querySelectorAll(".edit").forEach(e=>e.classList.add("hidden"));
        })
    })

    // Nút Xóa

    document.querySelectorAll(".delete-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const userId = btn.getAttribute("data-id");
            if (confirm("Bạn có chắc muốn xóa người dùng này không?")) {
                fetch("/admin/users?userId=" + userId, {
                    method: "DELETE"
                })
                    .then(response => {
                        if(response.ok){
                            // Xóa dòng table
                            const tr = btn.closest("tr");
                            tr.remove();
                        } else {
                            alert("Xóa thất bại!");
                        }
                    })
                    .catch(err => {
                        console.error(err);
                        alert("Có lỗi xảy ra!");
                    });
            }
        });
    });

    // Nút lưu (Sử dụng Fetch API thay vì submit form truyền thống)
    //document.querySelectorAll(".save-btn").forEach(btn => {
    //    btn.addEventListener("click", (e) => {
    //        e.preventDefault(); // NGĂN CHẶN SUBMIT FORM MẶC ĐỊNH
    //
    //        const tr = btn.closest("tr");
    //        const form = btn.closest("form");
    //
    //        const userId = form.querySelector('input[name="userId"]').value;
    //        const username = form.querySelector('input[name="username"]').value;
    //        const phone = form.querySelector('input[name="phone"]').value;
    //        const role = form.querySelector('select[name="role"]').value;
    //        const status = form.querySelector('select[name="status"]').value;
    //
    //        // Xây dựng dữ liệu form
    //        const formData = new URLSearchParams();
    //        formData.append('userId', userId);
    //        formData.append('username', username);
    //        formData.append('phone', phone);
    //        formData.append('role', role);
    //        formData.append('status', status);
    //
    //        fetch("admin/users", {
    //            method: 'POST',
    //            headers: {
    //                'Content-Type': 'application/x-www-form-urlencoded'
    //            },
    //            body: formData.toString()
    //        })
    //        .then(response => {
    //            if (response.ok) {
    //                // Cập nhật thành công: ẩn nút sửa và hiện nút xem
    //                tr.querySelectorAll(".view").forEach(v => v.classList.remove("hidden"));
    //                tr.querySelectorAll(".edit").forEach(e => e.classList.add("hidden"));
    //                // Reload trang để hiển thị dữ liệu mới nhất (tùy chọn)
    //                window.location.href = window.location.href;
    //            } else if (response.status === 403) {
    //                // Xử lý lỗi 403: Không có quyền
    //                return response.text().then(text => {
    //                    alert(text); // Hiển thị alert với thông báo từ Controller: "Bạn không có quyền thay đổi!"
    //                });
    //            } else {
    //                // Xử lý các lỗi khác
    //                alert("Cập nhật thất bại hoặc xảy ra lỗi khác!");
    //            }
    //        })
    //        .catch(err => {
    //            console.error('Lỗi kết nối:', err);
    //            alert("Lỗi kết nối đến máy chủ.");
    //        });
    //    });
    //});

    // Nút hủy
    document.querySelectorAll(".cancel-btn").forEach(btn=>{
        btn.addEventListener("click",()=>{
            const tr = btn.closest("tr");
            tr.querySelectorAll(".view").forEach(v=>v.classList.remove("hidden"));
            tr.querySelectorAll(".edit").forEach(v=>v.classList.add("hidden"));
        })
    })
</script>
</html>