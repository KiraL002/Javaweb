<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>
<%@ taglib prefix = "mytag" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <%-- ... phần head giữ nguyên ... --%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MAU Style - Trang chủ</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
        .hover-lift { transition: transform 0.3s ease, box-shadow 0.3s ease; }
        .hover-lift:hover { transform: translateY(-8px); box-shadow: 0 10px 20px rgba(0,0,0,0.1); }
    </style>
</head>
<body class="bg-gray-50 text-gray-900">

    <!-- 1. Include Menu -->
    <jsp:include page="Menu.jsp" />

    <!-- Hero Banner (Giữ nguyên) -->
    <section class="relative h-screen flex items-center justify-center overflow-hidden pt-16">
        <%-- ... nội dung hero banner giữ nguyên ... --%>
        <div class="absolute inset-0 z-0" style="background-image: url('https://images.unsplash.com/photo-1485968579580-b6d095142e6e?w=1600'); background-size: cover; background-position: center; background-attachment: fixed;">
            <div class="absolute inset-0 bg-black/40"></div>
        </div>
        <div class="relative z-10 text-center text-white px-4 max-w-4xl mx-auto">
            <h1 class="text-5xl md:text-7xl font-bold mb-6 leading-tight">
                Simple Fit – <span class="text-[#BFA77F]">Simple Life</span>
            </h1>
            <p class="text-xl md:text-2xl mb-8 text-white/90">Mặc đẹp không cần phức tạp</p>
            <a href="${pageContext.request.contextPath}/shop" class="bg-white text-black px-8 py-4 rounded-full font-semibold text-lg inline-flex items-center gap-3 hover:bg-[#BFA77F] hover:text-white transition-colors">
                Khám phá ngay
                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"></line><polyline points="12 5 19 12 12 19"></polyline></svg>
            </a>
        </div>
    </section>

    <!-- Categories -->
    <section class="py-20 px-4 bg-white">
        <div class="max-w-7xl mx-auto">
            <div class="text-center mb-12">
                <h2 class="text-4xl font-bold mb-4">Danh mục <span class="text-[#BFA77F]">nổi bật</span></h2>
                <p class="text-gray-600">Chọn phong cách phù hợp với bạn</p>
            </div>
            <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4 md:gap-6">
                
                <%-- ĐÃ DỌN DẸP: Sử dụng component CategoryCard --%>
                <c:forEach items="${categories}" var="cat">
                    <%-- 
                        Chúng ta gọi tag <mytag:CategoryCard ... />
                        và truyền "prop" category (là biến 'cat') vào
                    --%>
                    <mytag:CategoryCard category="${cat}" />
                </c:forEach>

            </div>
        </div>
    </section>

    <!-- New Arrivals -->
    <section class="py-20 px-4">
        <%-- ... phần New Arrivals giữ nguyên (vẫn dùng mytag:ProductCard) ... --%>
        <div class="max-w-7xl mx-auto">
            <div class="flex items-center justify-between mb-12">
                <h2 class="text-4xl font-bold">Bộ sưu tập mới</h2>
                <a href="${pageContext.request.contextPath}/shop" class="hidden md:flex items-center gap-2 text-black hover:text-[#BFA77F] font-medium transition-colors">
                    Xem tất cả
                </a>
            </div>
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 md:gap-6">
                <c:forEach items="${newProducts}" var="p">
                    <mytag:ProductCard product="${p}" />
                </c:forEach>
            </div>
        </div>
    </section>

    <!-- Best Sellers -->
    <section class="py-20 px-4 bg-white">
        <%-- ... phần Best Sellers giữ nguyên (vẫn dùng mytag:ProductCard) ... --%>
        <div class="max-w-7xl mx-auto">
            <div class="flex items-center justify-between mb-12">
                <h2 class="text-4xl font-bold">Sản phẩm bán chạy</h2>
                <a href="${pageContext.request.contextPath}/shop" class="hidden md:flex items-center gap-2 text-black hover:text-[#BFA77F] font-medium transition-colors">
                    Xem tất cả
                </a>
            </div>
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 md:gap-6">
                <c:forEach items="${bestSellers}" var="p">
                    <mytag:ProductCard product="${p}" />
                </c:forEach>
            </div>
        </div>
    </section>

<!--     Customer Reviews (Giữ nguyên) 
    <section class="py-20 px-4 bg-gradient-to-b from-white to-[#FAFAF9]">
        <%-- ... phần Customer Reviews giữ nguyên ... --%>
        <div class="max-w-4xl mx-auto" id="review-slider">
            <div class="text-center mb-12">
                <h2 class="text-4xl font-bold mb-4">Khách hàng nói gì về <span class="text-[#BFA77F]">MAU Style</span></h2>
            </div>
            
            <div class="relative overflow-hidden">
                 Review 1 
                <div class="review-item bg-white rounded-2xl p-8 md:p-12 shadow-xl" data-index="0">
                    <div class="flex items-center gap-4 mb-6">
                        <img src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=100" alt="Minh Anh" class="w-16 h-16 rounded-full object-cover"/>
                        <div>
                            <h4 class="font-semibold text-lg">Minh Anh</h4>
                        </div>
                    </div>
                    <p class="text-gray-700 text-lg leading-relaxed">"Chất vải rất đẹp, form dáng chuẩn. Mình rất hài lòng với MAU Style!"</p>
                </div>
                 Review 2 (ẩn) 
                <div class="review-item bg-white rounded-2xl p-8 md:p-12 shadow-xl hidden" data-index="1">
                    <div class="flex items-center gap-4 mb-6">
                        <img src="https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=100" alt="Tuấn Kiệt" class="w-16 h-16 rounded-full object-cover"/>
                        <div>
                            <h4 class="font-semibold text-lg">Tuấn Kiệt</h4>
                        </div>
                    </div>
                    <p class="text-gray-700 text-lg leading-relaxed">"Áo đẹp, giá hợp lý. Shiping nhanh, đóng gói cẩn thận. Sẽ ủng hộ tiếp!"</p>
                </div>
                 Review 3 (ẩn) 
                <div class="review-item bg-white rounded-2xl p-8 md:p-12 shadow-xl hidden" data-index="2">
                    <div class="flex items-center gap-4 mb-6">
                        <img src="https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=100" alt="Thu Hà" class="w-16 h-16 rounded-full object-cover"/>
                        <div>
                            <h4 class="font-semibold text-lg">Thu Hà</h4>
                        </div>
                    </div>
                    <p class="text-gray-700 text-lg leading-relaxed">"Phong cách tối giản đúng như mình thích. Mặc thoải mái và dễ phối đồ."</p>
                </div>
            </div>

             Indicators 
            <div class="flex justify-center gap-2 mt-6" id="review-indicators">
                <button class="review-indicator w-8 h-2 rounded-full transition-all bg-[#BFA77F]" data-index="0"></button>
                <button class="review-indicator w-2 h-2 rounded-full transition-all bg-gray-300" data-index="1"></button>
                <button class="review-indicator w-2 h-2 rounded-full transition-all bg-gray-300" data-index="2"></button>
            </div>
        </div>
    </section>-->

    <!-- 3. Include Footer -->
    <jsp:include page="Footer.jsp" />
    
    <!-- 4. Script cho Slider -->
    <script src="js/main.js"></script>
</body>
</html>

