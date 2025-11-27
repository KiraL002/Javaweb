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
<jsp:include page="Admin/UserManagement.jsp" />
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

</html>