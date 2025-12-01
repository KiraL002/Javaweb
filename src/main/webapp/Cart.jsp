<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>

<%-- Đặt locale để format tiền tệ sang VNĐ --%>
<fmt:setLocale value="vi_VN"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng - MAU Style</title>
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
            <h1 class="text-3xl md:text-4xl font-bold mb-8 pt-4">
                Giỏ hàng <span class="text-[#BFA77F]">của bạn</span>
            </h1>

            <%-- TRƯỜNG HỢP GIỎ HÀNG TRỐNG --%>
            <c:if test="${empty cartItems or cartItems.size() == 0}">
                <div class="flex flex-col items-center justify-center py-20 bg-white rounded-2xl shadow-sm border border-gray-100">
                    <div class="bg-gray-50 p-6 rounded-full mb-4">
                        <svg class="w-12 h-12 text-gray-400" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path><line x1="3" y1="6" x2="21" y2="6"></line><path d="M16 10a4 4 0 0 1-8 0"></path></svg>
                    </div>
                    <h2 class="text-2xl font-bold mb-2 text-gray-900">Giỏ hàng trống</h2>
                    <p class="text-gray-500 mb-8">Bạn chưa thêm sản phẩm nào vào giỏ hàng.</p>
                    <a href="${pageContext.request.contextPath}/shop" 
                       class="bg-black text-white px-8 py-3 rounded-xl font-semibold hover:bg-gray-800 transition-all hover:shadow-lg">
                        Tiếp tục mua sắm
                    </a>
                </div>
            </c:if>

            <%-- TRƯỜNG HỢP CÓ SẢN PHẨM --%>
            <c:if test="${not empty cartItems && cartItems.size() > 0}">
                <div class="flex flex-col lg:flex-row gap-8 items-start">
                    
                    <div class="w-full lg:flex-1 space-y-4">
                        <c:forEach items="${cartItems}" var="item">
                            
                            <div class="bg-white rounded-2xl p-4 md:p-6 shadow-sm border border-gray-100 flex flex-col md:flex-row gap-6 transition-all hover:shadow-md">
                                
                                <a href="${pageContext.request.contextPath}/detail?id=${item.product.id}" class="flex-shrink-0 mx-auto md:mx-0">
                                    <img src="${item.product.image}"
                                         alt="${item.product.name}"
                                         class="w-32 h-32 md:w-28 md:h-28 object-cover rounded-xl border border-gray-100"/>
                                </a>

                                <div class="flex-1 flex flex-col justify-between w-full">
                                    <div>
                                        <div class="flex justify-between items-start mb-2">
                                            <a href="${pageContext.request.contextPath}/detail?id=${item.product.id}"
                                               class="font-bold text-lg text-gray-900 hover:text-[#BFA77F] transition-colors line-clamp-2 pr-4">
                                                ${item.product.name}
                                            </a>
                                            
                                            <form action="${pageContext.request.contextPath}/cart-action" method="POST">
                                                <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                <input type="hidden" name="action" value="remove">
                                                <button type="submit" class="text-gray-400 hover:text-red-500 p-2 hover:bg-red-50 rounded-lg transition-colors group" title="Xóa sản phẩm">
                                                    <svg class="w-5 h-5 group-hover:stroke-red-500" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path><line x1="10" y1="11" x2="10" y2="17"></line><line x1="14" y1="11" x2="14" y2="17"></line></svg>
                                                </button>
                                            </form>
                                        </div>

                                        <div class="flex items-center gap-3 text-sm text-gray-500 mb-4">
                                            <span class="bg-gray-50 px-3 py-1 rounded-md border border-gray-100">Size: <span class="font-medium text-gray-900">${item.size}</span></span>
                                            <span class="bg-gray-50 px-3 py-1 rounded-md border border-gray-100">Màu: <span class="font-medium text-gray-900">${item.color}</span></span>
                                        </div>
                                    </div>

                                    <div class="flex items-center justify-between mt-auto pt-4 border-t border-dashed border-gray-100 md:pt-0 md:border-0">
                                        <form action="${pageContext.request.contextPath}/cart-action" method="POST" class="flex items-center border border-gray-300 rounded-lg bg-white h-10">
                                            <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                            <input type="hidden" name="action" value="update">

                                            <button type="submit" name="quantity" value="${item.quantity - 1}"
                                                    class="w-10 h-full flex items-center justify-center hover:bg-gray-100 text-gray-600 transition-colors rounded-l-lg disabled:opacity-50 disabled:cursor-not-allowed"
                                                    ${item.quantity <= 1 ? 'disabled' : ''}>
                                                <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"></line></svg>
                                            </button>

                                            <span class="w-10 text-center font-semibold text-gray-900 select-none">${item.quantity}</span>

                                            <button type="submit" name="quantity" value="${item.quantity + 1}"
                                                    class="w-10 h-full flex items-center justify-center hover:bg-gray-100 text-gray-600 transition-colors rounded-r-lg">
                                                <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
                                            </button>
                                        </form>

                                        <div class="text-right">
                                            <div class="font-bold text-xl text-gray-900">
                                                <fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencyCode="VND" minFractionDigits="0"/>
                                            </div>
                                            <div class="text-xs text-gray-400 mt-1">
                                                <fmt:formatNumber value="${item.product.price}" type="currency" currencyCode="VND" minFractionDigits="0"/> / cái
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="w-full lg:w-[380px] flex-shrink-0">
                        <div class="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 sticky top-24">
                            <h2 class="text-xl font-bold mb-6 text-gray-900">Tóm tắt đơn hàng</h2>

                            <div class="space-y-4 mb-6 pb-6 border-b border-gray-100">
                                <div class="flex justify-between text-gray-600">
                                    <span>Tạm tính</span>
                                    <span class="font-medium text-gray-900"><fmt:formatNumber value="${subtotal}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                                </div>
                                <div class="flex justify-between text-gray-600">
                                    <span>Phí vận chuyển</span>
                                    <span class="font-medium text-gray-900"><fmt:formatNumber value="${shipping}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                                </div>
                                <c:if test="${subtotal > 500000}">
                                    <div class="flex items-start gap-2 text-green-700 text-sm bg-green-50 p-3 rounded-lg border border-green-100">
                                        <svg class="w-5 h-5 flex-shrink-0" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>
                                        <span>Đơn hàng được <strong>miễn phí vận chuyển</strong></span>
                                    </div>
                                </c:if>
                            </div>

                            <div class="flex justify-between items-end mb-8">
                                <span class="text-lg font-medium text-gray-900">Tổng cộng</span>
                                <span class="text-2xl font-bold text-[#BFA77F]"><fmt:formatNumber value="${total}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                            </div>

                            <a href="Checkout.jsp" 
                               class="w-full bg-black hover:bg-gray-800 text-white py-4 rounded-xl font-bold text-lg flex items-center justify-center gap-2 transition-transform hover:scale-[1.02] shadow-lg">
                                Thanh toán ngay
                                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"></line><polyline points="12 5 19 12 12 19"></polyline></svg>
                            </a>

                            <div class="mt-6 flex items-center justify-center gap-2 text-gray-400 text-xs">
                                <svg class="w-3 h-3" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect><path d="M7 11V7a5 5 0 0 1 10 0v4"></path></svg>
                                <span>Bảo mật thanh toán 100%</span>
                            </div>

                        </div>
                    </div>
                </div>
            </c:if>
            
        </div>
    </div>

    <jsp:include page="Footer.jsp" />
</body>
</html>