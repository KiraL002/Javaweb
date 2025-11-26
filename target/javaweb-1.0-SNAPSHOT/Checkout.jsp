<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-[#FAFAF9]">

    <!-- 1. Include Menu -->
    <jsp:include page="Menu.jsp" />

    <!-- 2. Main Content -->
    <div class="min-h-screen pt-20">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <h1 class="text-3xl md:text-4xl font-bold mb-8">
                Thanh toán
            </h1>

            <form action="checkout" method="POST">
                <div class="grid lg:grid-cols-3 gap-8">
                    <!-- Cột bên trái: Thông tin giao hàng -->
                    <div class="lg:col-span-2 bg-white rounded-xl p-6 shadow-sm">
                        <h2 class="text-2xl font-bold mb-6">Thông tin giao hàng</h2>
                        <div class="space-y-4">
                            <div>
                                <label for="fullName" class="block text-sm font-medium text-gray-700 mb-1">Họ và tên</label>
                                <input type="text" name="fullName" id="fullName" value="${sessionScope.acc.fullName}"
                                       class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#BFA77F]" required>
                            </div>
                            <div>
                                <label for="address" class="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                                <input type="text" name="address" id="address" placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/TP"
                                       class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#BFA77F]" required>
                            </div>
                            <div>
                                <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
                                <input type="tel" name="phone" id="phone"
                                       class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#BFA77F]" required>
                            </div>
                            
                            <!-- Phương thức thanh toán -->
                            <div class="pt-4">
                                <h3 class="text-lg font-medium text-gray-900 mb-3">Phương thức thanh toán</h3>
                                <div class="space-y-3">
                                    <label class="flex items-center p-4 border rounded-lg cursor-pointer">
                                        <input type="radio" name="paymentMethod" value="COD" class="h-4 w-4 text-[#BFA77F] focus:ring-[#BFA77F]" checked>
                                        <span class="ml-3 block text-sm font-medium text-gray-700">Thanh toán khi nhận hàng (COD)</span>
                                    </label>
                                    <label class="flex items-center p-4 border rounded-lg cursor-pointer bg-gray-50 opacity-50">
                                        <input type="radio" name="paymentMethod" value="VNPay" class="h-4 w-4" disabled>
                                        <span class="ml-3 block text-sm font-medium text-gray-500">Thanh toán qua VNPay (Đang phát triển)</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Cột bên phải: Tóm tắt đơn hàng -->
                    <div class="lg:col-span-1">
                        <div class="bg-white rounded-xl p-6 shadow-sm sticky top-24">
                            <h2 class="text-xl font-bold mb-6">Tóm tắt đơn hàng</h2>
                            
                            <!-- Danh sách sản phẩm -->
                            <div class="space-y-4 max-h-60 overflow-y-auto mb-4 pr-2">
                                <c:forEach items="${cartItems}" var="item">
                                    <div class="flex gap-3">
                                        <img src="${item.displayImage}" alt="${item.name}" class="w-16 h-16 rounded-lg object-cover">
                                        <div class="flex-1">
                                            <p class="font-medium line-clamp-1">${item.name}</p>
                                            <p class="text-sm text-gray-500">Size: ${item.size} • Màu: ${item.color}</p>
                                        </div>
                                        <div class="text-right">
                                            <p class="font-medium"><fmt:formatNumber value="${item.displayPrice * item.quantity}" type="currency" currencyCode="VND" minFractionDigits="0"/></p>
                                            <p class="text-sm text-gray-500">SL: ${item.quantity}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Chi tiết giá -->
                            <div class="space-y-3 mb-6 pb-6 border-t pt-4">
                                <div class="flex justify-between text-gray-600">
                                    <span>Tạm tính:</span>
                                    <span><fmt:formatNumber value="${subtotal}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                                </div>
                                <div class="flex justify-between text-gray-600">
                                    <span>Phí vận chuyển:</span>
                                    <span>
                                        <c:choose>
                                            <c:when test="${shipping == 0}">Miễn phí</c:when>
                                            <c:otherwise><fmt:formatNumber value="${shipping}" type="currency" currencyCode="VND" minFractionDigits="0"/></c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </div>

                            <div class="flex justify-between text-xl font-bold mb-6">
                                <span>Tổng cộng:</span>
                                <span class="text-[#BFA77F]"><fmt:formatNumber value="${total}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                            </div>

                            <button type="submit" 
                                    class="w-full bg-black hover:bg-gray-800 text-white py-4 rounded-xl font-semibold flex items-center justify-center gap-2">
                                Đặt hàng
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- 3. Include Footer -->
    <jsp:include page="Footer.jsp" />

</body>
</html>
