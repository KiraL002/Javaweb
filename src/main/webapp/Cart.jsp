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

    <div class="min-h-screen pt-16">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <h1 class="text-3xl md:text-4xl font-bold mb-8 pt-8">
                Giỏ hàng <span class="text-[#BFA77F]">của bạn</span>
            </h1>

     
            <c:if test="${empty cartItems or cartItems.size() == 0}">
                <div class="flex items-center justify-center" style="min-height: 50vh;">
                    <div class="text-center max-w-md mx-auto px-4">
                        
                        <svg class="w-16 h-16 mx-auto mb-4 text-gray-400" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path><line x1="3" y1="6" x2="21" y2="6"></line><path d="M16 10a4 4 0 0 1-8 0"></path></svg>
                        <h2 class="text-2xl font-bold mb-4">Giỏ hàng trống</h2>
                        <p class="text-gray-600 mb-6">Hãy thêm sản phẩm vào giỏ hàng của bạn</p>
                        <a href="${pageContext.request.contextPath}/shop" 
                           class="bg-black text-white px-6 py-3 rounded-lg font-semibold hover:bg-gray-800 transition-colors">
                            Mua sắm ngay
                        </a>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty cartItems && cartItems.size() > 0}">
                <div class="grid lg:grid-cols-3 gap-8">
                    <div class="lg:col-span-2 space-y-4">
                        
                        <%-- === BẮT ĐẦU SỬA LỖI === --%>
                        <c:forEach items="${cartItems}" var="item">
      
                            <div class="bg-white rounded-xl p-4 md:p-6 shadow-sm border">
                                <div class="flex gap-4">
                                    
                                    <a href="${pageContext.request.contextPath}/detail?id=${item.product.id}" class="flex-shrink-0">
                                        
                                        <img src="${item.product.image}"
                                             alt="${item.product.name}"
                                             class="w-24 h-24 md:w-32 md:h-32 object-cover rounded-lg"/>
                                    </a>

                                    <div class="flex-1 min-w-0">
              
                                        <div class="flex justify-between gap-4 mb-2">
                                            <a href="${pageContext.request.contextPath}/detail?id=${item.product.id}"
                                               class="font-semibold text-lg hover:text-[#BFA77F] transition-colors line-clamp-1">
                                                
                                                ${item.product.name}
                                            </a>
                                            
                                            <form action="${pageContext.request.contextPath}/cart-action" method="POST">
                                                
                                                <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                
                                                <input type="hidden" name="action" value="remove">
                                                <button type="submit" class="text-gray-400 hover:text-red-500 transition-colors flex-shrink-0">
                                                    <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path><line x1="10" y1="11" x2="10" y2="17"></line><line x1="14" y1="11" x2="14" y2="17"></line></svg>
                                                </button>
                                            </form>
                                        </div>

                                        <div class="flex flex-wrap gap-2 text-sm text-gray-600 mb-3">
                                            <span>Size: ${item.size}</span>
                                            <span>• Màu: ${item.color}</span>
                                        </div>

                   
                                            <div class="flex items-center justify-between">
                                                <form action="${pageContext.request.contextPath}/cart-action" method="POST" class="flex items-center border-2 border-gray-200 rounded-lg">
                                                    <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                                    <input type="hidden" name="action" value="update">

                                                    <!-- Nút giảm -->
                                                    <button type="submit" name="quantity" value="${item.quantity - 1}"
                                                            class="p-2 hover:bg-gray-50 transition-colors"
                                                            ${item.quantity <= 1 ? 'disabled class="opacity-50 cursor-not-allowed"' : ''}>
                                                        <svg class="w-4 h-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <line x1="5" y1="12" x2="19" y2="12"></line>
                                                        </svg>
                                                    </button>

                                                    <span class="px-4 font-medium">${item.quantity}</span>

                                                    <!-- Nút tăng -->
                                                    <button type="submit" name="quantity" value="${item.quantity + 1}"
                                                            class="p-2 hover:bg-gray-50 transition-colors">
                                                        <svg class="w-4 h-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                        <line x1="12" y1="5" x2="12" y2="19"></line>
                                                        <line x1="5" y1="12" x2="19" y2="12"></line>
                                                        </svg>
                                                    </button>


                                                </form>

                                                <div class="text-right">
                                                    <div class="font-bold text-lg">
                                                        <fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencyCode="VND" minFractionDigits="0"/>
                                                    </div>
                                                    <div class="text-sm text-gray-500">
                                                        <fmt:formatNumber value="${item.product.price}" type="currency" currencyCode="VND" minFractionDigits="0"/> / sản phẩm
                                                    </div>
                                                </div>
                                            </div>

         
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        <%-- === KẾT THÚC SỬA LỖI === --%>
                    </div>

                    <div class="lg:col-span-1">
                        <div class="bg-white rounded-xl p-6 shadow-sm border sticky top-24">
                            <h2 class="text-xl font-bold mb-6">Tóm tắt đơn hàng</h2>

                            <div class="space-y-3 mb-6 pb-6 border-b">
                                <div class="flex justify-between text-gray-600">
                                    <span>Tạm tính:</span>
                                    <span><fmt:formatNumber value="${subtotal}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                                </div>
                                <div class="flex justify-between text-gray-600">
                                    <span>Phí vận chuyển:</span>
                                    <span><fmt:formatNumber value="${shipping}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                                </div>
                                <c:if test="${subtotal > 500000}">
                                    <p class="text-xs text-green-600">
                                        Bạn được miễn phí ship cho đơn trên 500.000đ
                                    </p>
                                </c:if>
                            </div>

                            <div class="flex justify-between text-xl font-bold mb-6">
                                <span>Tổng cộng:</span>
                                <span class="text-[#BFA77F]"><fmt:formatNumber value="${total}" type="currency" currencyCode="VND" minFractionDigits="0"/></span>
                            </div>

                    
                            <form action="Checkout.jsp" method="GET">
                                <button type="submit"
                                        class="w-full bg-black hover:bg-gray-800 text-white py-4 rounded-xl font-semibold flex items-center justify-center gap-2">
                                    Tiến hành thanh toán
                                </button>
                            </form>

                        </div>
                    </div>
                </div>
            </c:if>
            
        </div>
    </div>

    <jsp:include page="Footer.jsp" />
</body>
</html>