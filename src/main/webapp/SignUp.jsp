<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo tài khoản - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-[#FAFAF9]">

    <jsp:include page="Menu.jsp" />

    <div class="min-h-screen flex items-center justify-center pt-16">
        <div class="bg-white p-8 sm:p-12 rounded-2xl shadow-lg border border-gray-100 w-full max-w-md">
            
            <h1 class="text-3xl font-bold text-center mb-2">Tạo tài khoản</h1>
            <p class="text-gray-600 text-center mb-8">Bắt đầu mua sắm ngay!</p>

            <c:if test="${not empty requestScope.error}">
                <div class="bg-red-100 border border-red-300 text-red-700 px-4 py-3 rounded-lg mb-6" role="alert">
                    ${requestScope.error}
                </div>
            </c:if>

            <form action="signup" method="post" class="space-y-5">
                <div>
                    <label for="fullName" class="block text-sm font-medium text-gray-700 mb-1">
                        Họ và tên
                    </label>
                    <input type="text" id="fullName" name="fullName"
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black"
                           placeholder="Họ và tên..." required>
                </div>
                
                <div>
                    <label for="username" class="block text-sm font-medium text-gray-700 mb-1">
                        Tên đăng nhập
                    </label>
                    <input type="text" id="username" name="username"
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black"
                           placeholder="Tên đăng nhập..." required>
                </div>

                <div>
                    <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">
                        Số điện thoại
                    </label>
                    <input type="tel" id="phone" name="phone"
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black"
                           placeholder="Số điện thoại..." required>
                </div>

                <div>
                    <label for="password" class="block text-sm font-medium text-gray-700 mb-1">
                        Mật khẩu
                    </label>
                    <input type="password" id="password" name="password"
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black"
                           placeholder="Mật khẩu..." required>
                </div>

                <div>
                    <label for="repassword" class="block text-sm font-medium text-gray-700 mb-1">
                        Nhập lại mật khẩu
                    </label>
                    <input type="password" id="repassword" name="repassword"
                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black"
                           placeholder="Nhập lại mật khẩu..." required>
                </div>

                <button type="submit" 
                        class="w-full bg-black text-white font-semibold py-3 px-4 rounded-lg shadow-md hover:bg-gray-800 transition-colors duration-300">
                    Đăng ký
                </button>
            </form>

            <p class="text-center text-gray-600 mt-6">
                Đã có tài khoản? 
                <a href="login" class="font-semibold text-black hover:underline">
                    Đăng nhập
                </a>
            </p>
        </div>
    </div>
    
</body>
</html>