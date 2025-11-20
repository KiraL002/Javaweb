<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>
<%@ taglib prefix = "mytag" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sản phẩm - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Inter', sans-serif; }
        .hover-lift { transition: transform 0.3s ease, box-shadow 0.3s ease; }
        .hover-lift:hover { transform: translateY(-8px); box-shadow: 0 10px 20px rgba(0,0,0,0.1); }
        
        /* Style cho Form (Tương tự ảnh của bạn) */
        .filter-label {
            font-size: 1.125rem; /* text-lg */
            font-weight: 600; /* font-semibold */
            margin-bottom: 0.5rem; /* mb-2 */
            display: block;
        }
        .filter-input, .filter-select {
            width: 100%;
            padding: 0.75rem 1rem; /* py-3 px-4 */
            border: 1px solid #D1D5DB; /* border-gray-300 */
            border-radius: 0.5rem; /* rounded-lg */
            box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05); /* shadow-sm */
        }
        .filter-button {
            width: 100%;
            padding: 0.75rem;
            border-radius: 0.5rem;
            font-weight: 600;
            transition: background-color 0.2s;
        }
        .filter-button-primary {
            background-color: #3B82F6; /* bg-blue-500 */
            color: white;
        }
        .filter-button-primary:hover {
            background-color: #2563EB; /* bg-blue-700 */
        }
        .filter-button-secondary {
            background-color: white;
            color: #4B5563; /* text-gray-700 */
            border: 1px solid #D1D5DB;
        }
        .filter-button-secondary:hover {
            background-color: #F9FAFB; /* bg-gray-50 */
        }
    </style>
</head>
<body class="bg-[#FAFAF9]">

    <jsp:include page="Menu.jsp" />

    <div class="min-h-screen pt-20">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            
            <div class="mb-8">
                <h1 class="text-3xl md:text-4xl font-bold mb-2">
                     Sản phẩm <span class="text-[#BFA77F]">MAU Style</span>
                </h1>
                
                <p class="text-gray-600">
                    Tìm thấy ${totalProducts} sản phẩm
                </p>
                </div>

            <div class="flex flex-col md:flex-row gap-8">
                
                <aside class="w-full md:w-1/4 lg:w-1/5">
                    <form action="shop" method="get" class="space-y-6 bg-white p-6 rounded-lg shadow-sm border border-gray-200">
                        
                         <h2 class="text-2xl font-bold border-b pb-2">Bộ lọc</h2>

                        <div>
                            <label for="keyword" class="filter-label">Tìm kiếm</label>
                            
                            <input type="text" id="keyword" name="keyword" placeholder="Tên sản phẩm..."
                                   class="filter-input" value="${selectedKeyword}">
                            </div>

                         <div>
                            <label for="categoryId" class="filter-label">Danh mục</label>
                            <select id="categoryId" name="categoryId" class="filter-select">
                                <option value="0">Tất cả danh mục</option>
                                
                                <c:forEach items="${categoryList}" var="cat">
                                    <option value="${cat.id}" ${cat.id == selectedCategoryId ? 'selected' : ''}>
                                        ${cat.name}
                                    </option>
                                </c:forEach>
                                </select>
                        </div>
                        
                         <div>
                            <label for="brandId" class="filter-label">Thương hiệu</label>
                            <select id="brandId" name="brandId" class="filter-select">
                            
                                <option value="0">Tất cả thương hiệu</option>
                                
                                <c:forEach items="${brandList}" var="brand">
                                    <option value="${brand.id}" ${brand.id == selectedBrandId ? 'selected' : ''}>
                                        ${brand.name}
                                    </option>
                                </c:forEach>
                                </select>
                        </div>
                        
                         <div class="space-y-3">
                            <button type="submit" class="filter-button filter-button-primary">
                                <span class="flex items-center justify-center gap-2">
                                     <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="currentColor"><path d="M3 3h18v3.816l-7 7.001v6.368l-4-3v-3.368l-7-7.001V3z"/></svg>
                                    Lọc sản phẩm
                                </span>
                            </button>
                            <a href="shop" class="filter-button filter-button-secondary inline-block text-center">
                                Xóa bộ lọc
                            </a>
                        </div>
                    </form>
                </aside>

                <main class="w-full md:w-3/4 lg:w-4/5">
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 md:gap-6">
                        
                        <c:forEach items="${productList}" var="p">
                            <mytag:ProductCard product="${p}" />
                        </c:forEach>
                        </div>
                    
                    <c:if test="${empty productList}">
                        <div class="text-center py-16 bg-white rounded-lg shadow-sm border border-gray-200">
                             <h3 class="text-2xl font-semibold text-gray-700">Không tìm thấy sản phẩm</h3>
                            <p class="text-gray-500 mt-2">Vui lòng thử điều chỉnh lại bộ lọc của bạn.</p>
                            <a href="shop" class="mt-4 inline-block px-4 py-2 text-sm font-medium text-white bg-blue-500 rounded-lg hover:bg-blue-600">
                                Xóa bộ lọc
                            </a>
                        </div>
                    </c:if>
                    <nav class="flex justify-center items-center gap-2 mt-12">
                        <%-- Chỉ hiển thị nếu có nhiều hơn 1 trang --%>
                        <c:if test="${endPage > 1}">
                            
                            <%-- Nút 'Trang trước' --%>
                            <c:if test="${currentPage > 1}">
                                <a href="shop?page=${currentPage - 1}&keyword=${keyword}&categoryId=${categoryId}&brandId=${brandId}"
                                   class="w-10 h-10 flex items-center justify-center rounded-lg border border-gray-300 hover:bg-gray-100">
                                    <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" /></svg>
                                </a>
                            </c:if>

                            <%-- Các nút số trang --%>
                            <c:forEach begin="1" end="${endPage}" var="i">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <%-- Trang hiện tại (màu đen) --%>
                                        <span class="w-10 h-10 flex items-center justify-center rounded-lg bg-black text-white font-medium">
                                            ${i}
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <%-- Trang khác (link) --%>
                                        <a href="shop?page=${i}&keyword=${keyword}&categoryId=${categoryId}&brandId=${brandId}"
                                           class="w-10 h-10 flex items-center justify-center rounded-lg border border-gray-200 hover:bg-gray-100">
                                            ${i}
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <%-- Nút 'Trang sau' --%>
                            <c:if test="${currentPage < endPage}">
                                <a href="shop?page=${currentPage + 1}&keyword=${keyword}&categoryId=${categoryId}&brandId=${brandId}"
                                   class="w-10 h-10 flex items-center justify-center rounded-lg border border-gray-300 hover:bg-gray-100">
                                    <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" /></svg>
                                </a>
                            </c:if>
                            
                        </c:if>
                    </nav>
                    </main>
                
            </div> </div>
    </div>

    <jsp:include page="Footer.jsp" />

</body>
</html>