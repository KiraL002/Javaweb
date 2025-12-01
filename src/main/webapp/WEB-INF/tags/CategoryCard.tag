<%-- 
    Đây là Component (Tag File) cho Category Card.
--%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 
    Định nghĩa props
--%>
<%@ attribute name="category" required="true" type="com.mycompany.javaweb.entity.Category" %>

<%--
   SỬA LỖI LOGIC: 
   Đổi tham số từ "category=Name" sang "categoryId=ID" 
   để khớp với ShopControl.java
--%>
<a href="${pageContext.request.contextPath}/shop?categoryId=${category.id}" 
   class="group block relative overflow-hidden rounded-xl aspect-[4/5] hover-lift">
    
    <img src="${category.image}" 
         alt="${category.name}" 
         class="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
    />
    
    <div class="absolute inset-0 bg-gradient-to-t from-black/70 via-black/20 to-transparent"></div>
    
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