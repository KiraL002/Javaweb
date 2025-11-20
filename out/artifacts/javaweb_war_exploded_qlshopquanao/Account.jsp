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
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-[#FAFAF9]">

<jsp:include page="Menu.jsp" />

<div class="min-h-screen pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 class="text-3xl md:text-4xl font-bold mb-8">
            Tài khoản <span class="text-[#BFA77F]">của bạn</span>
        </h1>

        <div class="grid md:grid-cols-3 gap-8">
            <div class="md:col-span-1">
                <div class="bg-white rounded-xl p-6 shadow-sm sticky top-24">
                    <c:if test="${sessionScope.account != null}">
                        <div class="flex items-center gap-4 mb-6 pb-6 border-b">
                            <div class="w-16 h-16 bg-[#BFA77F] rounded-full flex items-center justify-center text-white text-2xl font-bold">
                                    ${sessionScope.account.username.substring(0, 1).toUpperCase()}
                            </div>
                            <div>
                                <h3 class="font-semibold text-lg">${sessionScope.account.fullName}</h3>
                                <p class="text-sm text-gray-600">${sessionScope.account.username}</p>
                            </div>
                        </div>
                    </c:if>

                    <nav class="space-y-2">
                        <c:if test="${sessionScope.account.role.equals('USER')}">
                            <a href="account?tab=orders"
                               class="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors
                                           ${activeTab == 'orders' ? 'bg-gray-100 font-medium' : 'text-gray-600 hover:bg-gray-50'}">
                                <svg class="w-5 h-5" ... (icon) ...></svg>
                                Đơn hàng của tôi
                            </a>
                        </c:if>
                        <a href="account?tab=profile"
                           class="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors
                                       ${activeTab == 'profile' ? 'bg-gray-100 font-medium' : 'text-gray-600 hover:bg-gray-50'}">
                            <svg class="w-5 h-5" ... (icon) ...></svg>
                            Thông tin cá nhân
                        </a>
                    </nav>
                    <div class="mt-6 pt-6 border-t">
                        <a href="logout" class="w-full flex items-center gap-3 px-4 py-3 rounded-lg text-red-600 ...">
                            <svg class="w-5 h-5" ... (icon) ...></svg>
                            Đăng xuất
                        </a>
                    </div>
                </div>
            </div>

            <div class="md:col-span-2">
                <div class="bg-white rounded-xl p-6 sm:p-8 shadow-sm">

                    <c:choose>

                        <%-- 1. TAB ĐƠN HÀNG (orders) --%>
                        <c:when test="${activeTab == 'orders'}">
                            <h2 class="text-2xl font-bold mb-6">Đơn hàng của tôi</h2>
                            <div class="text-center py-12">
                                <svg class="w-16 h-16 mx-auto mb-4 text-gray-300" ... (icon) ...></svg>
                                <h3 class="text-xl font-semibold text-gray-800 mb-2">Chưa có đơn hàng</h3>
                                <p class="text-gray-500 mb-6">Bạn chưa có đơn hàng nào</p>
                                <a href="shop" class="inline-block bg-black text-white px-6 py-3 rounded-lg font-semibold hover:bg-gray-800 transition-colors">
                                    Mua sắm ngay
                                </a>
                            </div>
                        </c:when>

                        <%-- 3. TAB THÔNG TIN CÁ NHÂN (profile) --%>
                        <c:when test="${activeTab == 'profile'}">

                            <h2 class="text-2xl font-bold mb-6">Thông tin cá nhân</h2>

                            <c:if test="${param.update == 'success'}">
                                <div class="mb-4 p-4 bg-green-100 text-green-700 rounded-lg text-center">Cập nhật thông tin thành công!</div>
                            </c:if>

                            <form action="update-profile" method="POST" class="space-y-5">
                                <input type="hidden" name="action" value="updateInfo">

                                <div>
                                    <label for="username" class="block text-sm font-medium text-gray-700 mb-1">Tên đăng nhập</label>
                                    <input type="text" id="username" name="username" value="${sessionScope.account.username}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg bg-gray-100 cursor-not-allowed"
                                           readonly>
                                </div>
                                <div>
                                    <label for="fullName" class="block text-sm font-medium text-gray-700 mb-1">Họ và tên</label>
                                    <input type="text" id="fullName" name="fullName" value="${sessionScope.account.fullName}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black" required>
                                </div>
                                <div>
                                    <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
                                    <input type="tel" id="phone" name="phone" value="${sessionScope.account.phone}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black" required>
                                </div>
                                <div>
                                    <label for="address" class="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                                    <input type="text" id="address" name="address" value="${sessionScope.account.address}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black">
                                </div>
                                <div class="text-right pt-2">
                                    <button type="submit" class="bg-black text-white px-6 py-3 rounded-lg font-semibold hover:bg-gray-800 transition-colors">
                                        Lưu thay đổi
                                    </button>
                                </div>
                            </form>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp" />

</body>
</html>