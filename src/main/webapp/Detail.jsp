<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>
<%@ taglib prefix = "mytag" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
        .hover-lift { transition: transform 0.3s ease, box-shadow 0.3s ease; }
        .hover-lift:hover { transform: translateY(-8px); box-shadow: 0 10px 20px rgba(0,0,0,0.1); }
        .fade-in { animation: fadeIn 0.5s ease forwards; }
        .fade-out { animation: fadeOut 0.5s ease 2.5s forwards; }
        @keyframes fadeIn { from { opacity: 0; transform: translateY(-20px); } to { opacity: 1; transform: translateY(0); } }
        @keyframes fadeOut { from { opacity: 1; transform: translateY(0); } to { opacity: 0; transform: translateY(-20px); } }
        
        /* Ẩn nút radio của form */
        .option-radio { display: none; }
        .option-label {
            /* Style đẹp cho nút chọn Size/Màu */
            min-width: 3rem;
            height: 3rem;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #e5e7eb;
            border-radius: 0.5rem;
            cursor: pointer;
            transition: all 0.2s ease;
            font-weight: 500;
            padding: 0 1rem;
        }
        .option-label:hover {
            border-color: #000;
        }
        .option-radio:checked + .option-label {
            background-color: #000;
            color: #fff;
            border-color: #000;
        }
    </style>
</head>
<body class="bg-[#FAFAF9]">

    <jsp:include page="Menu.jsp" />

    <div class="min-h-screen pt-20">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <a href="${pageContext.request.contextPath}/shop" 
               class="inline-flex items-center gap-2 text-gray-600 hover:text-black mb-8 transition-colors">
                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>
                Quay lại
            </a>

            <c:if test="${not empty param.cartSuccess}">
                <div class="fixed top-24 right-4 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg z-50 flex items-center gap-2 fade-in fade-out">
                    <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path><polyline points="22 4 12 14.01 9 11.01"></polyline></svg>
                    Đã thêm vào giỏ hàng!
                </div>
            </c:if>

            <c:choose>
                <c:when test="${empty product}">
                    <div class="text-center py-20">
                        <h2 class="text-2xl font-bold mb-4">Không tìm thấy sản phẩm</h2>
                        <a href="${pageContext.request.contextPath}/shop">
                            <button class="bg-black text-white px-6 py-3 rounded-lg font-medium">Quay lại Shop</button>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <form action="add-to-cart" method="post">
                        <input type="hidden" name="productId" value="${product.id}" />
                        
                        <div class="grid md:grid-cols-2 gap-8 md:gap-12 mb-20">
                            <div>
                                <div class="sticky top-24">
                                    <div class="bg-white rounded-2xl overflow-hidden mb-4 aspect-square">
                                        <img id="main-image"
                                             src="${product.displayImage}"
                                             alt="${product.name}"
                                             class="w-full h-full object-cover"
                                        />
                                    </div>
                                    
                                    <c:if test="${product.images.size() > 1}">
                                        <div class="grid grid-cols-5 gap-3">
                                            <c:forEach items="${product.images}" var="image" varStatus="loop">
                                                <button type="button" 
                                                        class="aspect-square rounded-lg overflow-hidden border-2 transition-all ${loop.index == 0 ? 'border-black' : 'border-transparent hover:border-gray-300'}"
                                                        onclick="document.getElementById('main-image').src='${image}'; document.querySelectorAll('.thumbnail-btn').forEach(btn => btn.classList.add('border-transparent')); this.classList.remove('border-transparent');">
                                                    <img src="${image}" alt="${product.name} ${loop.index + 1}" class="w-full h-full object-cover thumbnail-btn"/>
                                                </button>
                                            </c:forEach>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <div class="space-y-8"> <div>
                                    <c:if test="${product.isNew}">
                                        <span class="inline-block bg-black text-white text-xs px-3 py-1 rounded-full font-medium mb-3 tracking-wide">
                                            SẢN PHẨM MỚI
                                        </span>
                                    </c:if>
                                    <h1 class="text-3xl md:text-4xl font-bold mb-2 text-gray-900">${product.name}</h1>
                                    
                                    <div class="flex items-center gap-3 mb-4">
                                        <span class="text-2xl md:text-3xl font-bold text-gray-900">
                                            <fmt:formatNumber value="${product.displayPrice}" type="currency" currencyCode="VND" minFractionDigits="0"/>
                                        </span>
                                        <c:if test="${product.hasDiscount}">
                                            <span class="text-lg text-gray-400 line-through">
                                                <fmt:formatNumber value="${product.price}" type="currency" currencyCode="VND" minFractionDigits="0"/>
                                            </span>
                                            <span class="bg-[#BFA77F] text-white text-xs px-2 py-1 rounded-md font-bold">
                                                -${product.discountPercent}%
                                            </span>
                                        </c:if>
                                    </div>
                                    <p class="text-gray-600 leading-relaxed text-base">
                                        ${product.description}
                                    </p>
                                </div>

                                <c:if test="${not empty product.sizes}">
                                    <div>
                                        <label class="font-semibold mb-3 block text-gray-800">Chọn size:</label>
                                        <div class="flex flex-wrap gap-3">
                                            <c:forEach items="${product.sizes}" var="size" varStatus="loop">
                                                <input type="radio" name="size" value="${size}" id="size-${size}" class="option-radio" ${loop.index == 0 ? 'checked' : ''} required>
                                                <label for="size-${size}" class="option-label">${size}</label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty product.colors}">
                                    <div>
                                        <label class="font-semibold mb-3 block text-gray-800">Chọn màu sắc:</label>
                                        <div class="flex flex-wrap gap-3">
                                            <c:forEach items="${product.colors}" var="color" varStatus="loop">
                                                <input type="radio" name="color" value="${color}" id="color-${color}" class="option-radio" ${loop.index == 0 ? 'checked' : ''} required>
                                                <label for="color-${color}" class="option-label">${color}</label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>

                                <div>
                                    <label class="font-semibold mb-3 block text-gray-800">Số lượng:</label>
                                    <div class="flex items-center gap-4">
                                        <div class="flex items-center border border-gray-300 rounded-lg">
                                            <button type="button" class="p-3 hover:bg-gray-100 transition-colors rounded-l-lg" onclick="updateQuantity(-1)">
                                                <svg class="w-4 h-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="5" y1="12" x2="19" y2="12"></line></svg>
                                            </button>
                                            <input type="number" id="quantity-input" name="quantity" value="1" class="px-2 font-medium text-center w-16 border-0 focus:ring-0 [appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none">
                                            <button type="button" class="p-3 hover:bg-gray-100 transition-colors rounded-r-lg" onclick="updateQuantity(1)">
                                                <svg class="w-4 h-4" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
                                            </button>
                                        </div>
                                        <span class="text-sm text-gray-500 font-medium">
                                            <c:choose>
                                                <c:when test="${product.stock > 0}">
                                                    ${product.stock} sản phẩm có sẵn
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-red-600 font-semibold">Hết hàng</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                </div>
                                    
                                <div class="pt-2">
                                    <button type="submit"
                                            class="w-full text-white py-4 rounded-xl font-bold text-lg flex items-center justify-center gap-3 transition-transform hover:scale-[1.02] shadow-lg
                                            ${product.stock == 0 ? 'bg-gray-400 cursor-not-allowed' : 'bg-black hover:bg-gray-800'}"
                                            ${product.stock == 0 ? 'disabled' : ''}>
                                        
                                        <svg class="w-6 h-6" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path><line x1="3" y1="6" x2="21" y2="6"></line><path d="M16 10a4 4 0 0 1-8 0"></path></svg>
                                        
                                        ${product.stock == 0 ? 'Hết hàng' : 'Thêm vào giỏ'}
                                    </button>
                                </div>

                                <div class="grid grid-cols-3 gap-4 pt-8 border-t mt-8">
                                    <div class="text-center group">
                                        <div class="w-12 h-12 mx-auto mb-2 bg-gray-50 rounded-full flex items-center justify-center group-hover:bg-[#BFA77F]/10 transition-colors">
                                            <svg class="w-6 h-6 text-[#BFA77F]" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="1" y="3" width="15" height="13"></rect><polygon points="16 8 20 8 23 11 23 16 16 16 16 8"></polygon><circle cx="5.5" cy="18.5" r="2.5"></circle><circle cx="18.5" cy="18.5" r="2.5"></circle></svg>
                                        </div>
                                        <p class="text-sm font-semibold">Giao hàng nhanh</p>
                                        <p class="text-xs text-gray-500">2-3 ngày</p>
                                    </div>
                                    <div class="text-center group">
                                        <div class="w-12 h-12 mx-auto mb-2 bg-gray-50 rounded-full flex items-center justify-center group-hover:bg-[#BFA77F]/10 transition-colors">
                                            <svg class="w-6 h-6 text-[#BFA77F]" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="23 4 23 10 17 10"></polyline><polyline points="1 20 1 14 7 14"></polyline><path d="M3.51 9a9 9 0 0 1 14.85-3.36L20.49 9"></path><path d="M20.49 15a9 9 0 0 1-14.85 3.36L3.51 15"></path></svg>
                                        </div>
                                        <p class="text-sm font-semibold">Đổi trả 7 ngày</p>
                                        <p class="text-xs text-gray-500">Miễn phí</p>
                                    </div>
                                    <div class="text-center group">
                                        <div class="w-12 h-12 mx-auto mb-2 bg-gray-50 rounded-full flex items-center justify-center group-hover:bg-[#BFA77F]/10 transition-colors">
                                            <svg class="w-6 h-6 text-[#BFA77F]" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path></svg>
                                        </div>
                                        <p class="text-sm font-semibold">Bảo hành</p>
                                        <p class="text-xs text-gray-500">Chính hãng</p>
                                    </div>
                                </div>
                                
                                <div class="mt-6 space-y-3 pt-6 border-t">
                                    <c:if test="${not empty product.material}">
                                        <div class="flex gap-2">
                                            <span class="font-semibold min-w-[100px]">Chất liệu:</span>
                                            <span class="text-gray-600">${product.material}</span>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty product.careInstructions}">
                                        <div class="flex gap-2">
                                            <span class="font-semibold min-w-[100px]">Bảo quản:</span>
                                            <span class="text-gray-600">${product.careInstructions}</span>
                                        </div>
                                    </c:if>
                                </div>

                            </div>
                        </div>
                    </form>
<!--                    <div class="mt-16 border-t pt-12">
                        <h2 class="text-2xl md:text-3xl font-bold mb-8">Đánh giá sản phẩm</h2>

                        <div class="grid md:grid-cols-2 gap-12">
                            <div class="space-y-6">
                                <c:forEach var="rv" items="${reviewList}">
                                    <div class="border p-6 rounded-xl bg-white shadow-sm">
                                        <div class="flex justify-between items-start mb-2">
                                            <div class="font-semibold text-lg">${rv.userName}</div>
                                            <div class="text-yellow-500 tracking-widest text-sm">
                                                <c:forEach begin="1" end="${rv.star}">★</c:forEach>
                                                <c:forEach begin="${rv.star + 1}" end="5">☆</c:forEach>
                                            </div>
                                        </div>
                                        <p class="text-gray-600 italic mb-3">"${rv.comment}"</p>
                                        <p class="text-xs text-gray-400">
                                            <fmt:formatDate value="${rv.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                                        </p>
                                    </div>
                                </c:forEach>

                                <c:if test="${empty reviewList}">
                                    <p class="text-gray-500 italic">Chưa có đánh giá nào cho sản phẩm này.</p>
                                </c:if>
                            </div>

                            <div class="bg-gray-50 p-6 rounded-xl border">
                                <h3 class="text-xl font-bold mb-4">Viết đánh giá của bạn</h3>
                                
                                <%-- KIỂM TRA QUYỀN ĐÁNH GIÁ --%>
                                <c:choose>
                                    <%-- TRƯỜNG HỢP 1: ĐƯỢC PHÉP ĐÁNH GIÁ --%>
                                    <c:when test="${canReview}">
                                        <form action="review" method="post" class="space-y-4">
                                            <input type="hidden" name="productId" value="${product.id}" />

                                            <div>
                                                <label class="block mb-2 font-medium">Chọn số sao:</label>
                                                <div class="flex gap-4">
                                                    <select name="star" class="border p-2 rounded-lg w-full bg-white focus:ring-2 focus:ring-black focus:border-transparent outline-none">
                                                        <option value="5">5 Sao (Tuyệt vời)</option>
                                                        <option value="4">4 Sao (Tốt)</option>
                                                        <option value="3">3 Sao (Bình thường)</option>
                                                        <option value="2">2 Sao (Tệ)</option>
                                                        <option value="1">1 Sao (Rất tệ)</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div>
                                                <label class="block mb-2 font-medium">Nội dung:</label>
                                                <textarea name="comment" rows="4" class="w-full border p-3 rounded-lg focus:ring-2 focus:ring-black focus:border-transparent outline-none" placeholder="Chia sẻ cảm nhận của bạn về sản phẩm..." required></textarea>
                                            </div>

                                            <button type="submit" class="w-full bg-black hover:bg-gray-800 text-white font-bold py-3 rounded-lg transition-colors">
                                                Gửi đánh giá
                                            </button>
                                        </form>
                                    </c:when>
                                    
                                    <%-- TRƯỜNG HỢP 2: CHƯA ĐĂNG NHẬP --%>
                                    <c:when test="${sessionScope.account == null}">
                                        <div class="text-center py-8 px-4 bg-white rounded-lg border border-dashed border-gray-300">
                                            <svg class="w-12 h-12 mx-auto mb-3 text-gray-300" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" /></svg>
                                            <p class="text-gray-600 mb-4">Vui lòng đăng nhập để viết đánh giá.</p>
                                            <a href="login" class="inline-block bg-black text-white px-6 py-2 rounded-lg font-medium hover:bg-gray-800 transition-colors">Đăng nhập ngay</a>
                                        </div>
                                    </c:when>

                                    <%-- TRƯỜNG HỢP 3: ĐÃ ĐĂNG NHẬP NHƯNG CHƯA MUA / CHƯA NHẬN HÀNG --%>
                                    <c:otherwise>
                                        <div class="text-center py-8 px-4 bg-white rounded-lg border border-dashed border-gray-300">
                                            <svg class="w-12 h-12 mx-auto mb-3 text-gray-300" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.993 1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 0 1-1.12-1.243l1.264-12A1.125 1.125 0 0 1 5.513 7.5h12.974c.576 0 1.059.435 1.119 1.007Z" /></svg>
                                            <p class="text-gray-600 font-medium">Bạn chưa thể đánh giá sản phẩm này.</p>
                                            <p class="text-sm text-gray-500 mt-1">Chỉ những khách hàng đã mua và nhận hàng thành công mới được phép đánh giá.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>-->
                    <c:if test="${not empty relatedProducts}">
                        <div class="mt-20 border-t pt-12">
                            <h2 class="text-2xl md:text-3xl font-bold mb-8">
                                Sản phẩm <span class="text-[#BFA77F]">tương tự</span>
                            </h2>
                            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 md:gap-6">
                                <c:forEach items="${relatedProducts}" var="p">
                                    <mytag:ProductCard product="${p}" />
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <jsp:include page="Footer.jsp" />
    
    <script>
        function updateQuantity(amount) {
            const input = document.getElementById('quantity-input');
            let currentValue = parseInt(input.value);
            currentValue += amount;
            if (currentValue < 1) {
                currentValue = 1;
            }
            input.value = currentValue;
        }
        
        // Gắn sự kiện cho thumbnail buttons sau khi trang tải
        document.addEventListener('DOMContentLoaded', () => {
            const mainImage = document.getElementById('main-image');
            const thumbnails = document.querySelectorAll('.thumbnail-btn');
            
            thumbnails.forEach(button => {
                button.addEventListener('click', () => {
                    // Cập nhật ảnh chính
                    mainImage.src = button.querySelector('img').src;
                    
                    // Xóa viền đen ở tất cả các nút
                    thumbnails.forEach(btn => {
                        btn.parentElement.classList.remove('border-black');
                        btn.parentElement.classList.add('border-transparent');
                    });
                    
                    // Thêm viền đen cho nút được nhấp
                    button.parentElement.classList.add('border-black');
                    button.parentElement.classList.remove('border-transparent');
                });
            });
        });
    </script>
</body>
</html>