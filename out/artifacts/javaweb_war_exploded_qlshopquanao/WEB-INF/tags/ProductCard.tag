<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<%-- 
  Định nghĩa "props" (giữ nguyên)
  Nó nhận một "prop" tên là "product"
  với kiểu dữ liệu là "Product".
--%>
<%@ attribute name="product" required="true" type="com.mycompany.javaweb.entity.Product" %>

<%--
  PHẦN CODE ĐÃ ĐƯỢC SỬA VÀ ĐƠN GIẢN HÓA
--%>

<%-- Đặt locale để format tiền tệ sang VNĐ --%>
<fmt:setLocale value="vi_VN"/>

<a href="${pageContext.request.contextPath}/detail?id=${product.id}" 
   class="group relative block bg-white rounded-lg shadow-sm overflow-hidden hover-lift">
    
    <div class="relative overflow-hidden aspect-[3/4] mb-4">
        <img src="${product.image}" 
             alt="${product.name}" 
             class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110">

        <div class="absolute top-3 right-3 flex flex-col gap-2 opacity-0 group-hover:opacity-100 transition-opacity duration-300">
            <%-- ĐÃ XÓA NÚT YÊU THÍCH (Trái tim) --%>
        </div>
        
        <%-- 
          ======================================================
          KHỐI "THÊM VÀO GIỎ" ĐÃ BỊ XÓA HOÀN TOÀN KHỎI ĐÂY
          THEO YÊU CẦU CỦA BẠN.
          ======================================================
        --%>
        
    </div>

    <div class="p-4 pt-0">
        <h3 class="font-medium text-gray-900 group-hover:text-[#BFA77F] transition-colors line-clamp-1">
            ${product.name}
        </h3>
        
        <div class="flex items-center gap-2 mt-1">
            <span class="font-semibold text-lg text-black">
                <%-- ĐÃ SỬA: Dùng "price" thay vì "displayPrice" --%>
                <fmt:formatNumber value="${product.price}" type="currency" currencyCode="VND" minFractionDigits="0"/>
            </span>
            </div>
    </div>
</a>