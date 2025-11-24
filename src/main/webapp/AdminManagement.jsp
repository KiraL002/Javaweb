<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>
<%@ taglib prefix = "mytag" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tài khoản - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- Font Awesome Free CDN -->
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<style>
    td {
        position: relative;
    }
    .edit {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
    }


</style>
<body class="bg-[#FAFAF9]">

<jsp:include page="Menu.jsp" />

<div class="min-h-screen pt-20">

    <div class="grid md:grid-cols-7 gap-8">
        <div class="md:col-span-1">
            <div class="flex flex-col w-full bg-white shadow-sm rounded-lg overflow-hidden md:mt-0 lg:mt-0">
                <a href="admin?tab=users"
                   class="w-full text-left px-6 py-3 font-medium transition-colors duration-200 border-l-4
              <c:if test='${activeTab == "users"}'>text-black bg-gray-100 border-[#BFA77F] hover:bg-gray-200</c:if>
              <c:if test='${activeTab != "users"}'>text-gray-700 border-transparent hover:text-black hover:bg-gray-50</c:if>">
                    Người dùng
                </a>

                <a href="admin?tab=orders"
                   class="w-full text-left px-6 py-3 font-medium transition-colors duration-200 border-l-4
              <c:if test='${activeTab == "orders"}'>text-black bg-gray-100 border-[#BFA77F] hover:bg-gray-200</c:if>
              <c:if test='${activeTab != "orders"}'>text-gray-700 border-transparent hover:text-black hover:bg-gray-50</c:if>">
                    Đơn hàng
                </a>

                <a href="admin?tab=products"
                   class="w-full text-left px-6 py-3 font-medium transition-colors duration-200 border-l-4
              <c:if test='${activeTab == "products"}'>text-black bg-gray-100 border-[#BFA77F] hover:bg-gray-200</c:if>
              <c:if test='${activeTab != "products"}'>text-gray-700 border-transparent hover:text-black hover:bg-gray-50</c:if>">
                    Sản phẩm
                </a>
            </div>
        </div>

    <div class="md:col-span-6">
    <div class="pt-10">
    <c:choose>
    <%--        Người dùng--%>
        <c:when test="${activeTab == 'users'}">
            <h4 class="text-2xl md:text-3xl font-bold mb-8">
                Quản lý <span class="text-[#BFA77F]">người dùng</span>
            </h4>
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

        </c:when>
        <c:when test="${activeTab == 'orders'}">
            <h4 class="text-2xl md:text-3xl font-bold mb-8">
                Quản lý <span class="text-[#BFA77F]">đơn hàng</span>
            </h4>
            <jsp:include page="Admin/OrderManagement.jsp" />
        </c:when>
        <c:when test="${activeTab == 'products'}">
            <h4 class="text-2xl md:text-3xl font-bold mb-8">
                Quản lý <span class="text-[#BFA77F]">sản phẩm</span>
            </h4>
        </c:when>
    </c:choose>
    </div>
    </div>
    </div>

</div>

<jsp:include page="Footer.jsp" />

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