<%-- 
    Đây là Component (Tag File) cho Category Card.
    Nó tương đương với file CategoryCard.jsx của bạn.
--%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 
    Đây là định nghĩa "props" cho component này.
    Chúng ta khai báo rằng tag này BẮT BUỘC phải nhận một "prop" tên là "category"
    với kiểu dữ liệu là lớp "Category" của chúng ta.
--%>
<%@ attribute name="category" required="true" type="com.mycompany.javaweb.entity.Category" %>

<%--
    Phần HTML bên dưới được lấy từ Home.jsp
    và lấy cảm hứng từ logic của CategoryCard.jsx (ví dụ: hiệu ứng hover)
--%>
<a href="${pageContext.request.contextPath}/shop?category=${category.name}" 
   class="group block relative overflow-hidden rounded-xl aspect-[4/5] hover-lift">
    
    <!-- Image -->
    <img src="${category.image}" 
         alt="${category.name}" 
         class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
    />
    
    <!-- Overlay -->
    <div class="absolute inset-0 bg-gradient-to-t from-black/70 via-black/20 to-transparent"></div>
    
    <!-- Content (Lấy từ React) -->
    <div class="absolute bottom-0 left-0 right-0 p-6 text-white">
        <h3 class="text-2xl font-bold mb-2 transform transition-transform duration-300 group-hover:translate-x-2">
            ${category.name}
        </h3>
        <div class="flex items-center gap-2 text-sm opacity-0 group-hover:opacity-100 transition-all duration-300 transform translate-y-2 group-hover:translate-y-0">
            <span>Khám phá ngay</span>
            <span class="text-[#BFA77F]">→</span>
        </div>
    </div>
</a>
