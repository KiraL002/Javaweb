<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>
<%@ taglib prefix = "mytag" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản trị viên - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
    <style>
        body { font-family: 'Inter', sans-serif; }
        /* CSS cho Modal */
        .modal { opacity: 0; visibility: hidden; transition: all 0.3s ease; }
        .modal.active { opacity: 1; visibility: visible; }
        .modal-content { transform: scale(0.9); transition: all 0.3s ease; }
        .modal.active .modal-content { transform: scale(1); }
    </style>
</head>
<body class="bg-[#FAFAF9]">

<jsp:include page="Menu.jsp" />

<div class="min-h-screen pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8"> <div class="grid md:grid-cols-4 gap-8"> <div class="md:col-span-1">
                <div class="flex flex-col w-full bg-white shadow-sm rounded-xl overflow-hidden sticky top-24">
                    <div class="px-6 py-4 bg-gray-50 border-b">
                        <h3 class="font-bold text-gray-800 uppercase text-xs tracking-wider">Menu Quản lý</h3>
                    </div>
                    
                    <a href="admin?tab=users"
                       class="w-full text-left px-6 py-4 font-medium transition-colors duration-200 border-l-4
                       ${activeTab == 'users' ? 'border-[#BFA77F] bg-gray-50 text-black' : 'border-transparent text-gray-600 hover:bg-gray-50 hover:text-black'}">
                        <i class="fa-solid fa-users mr-3 text-sm"></i> Người dùng
                    </a>

                    <a href="admin?tab=orders"
                       class="w-full text-left px-6 py-4 font-medium transition-colors duration-200 border-l-4
                       ${activeTab == 'orders' ? 'border-[#BFA77F] bg-gray-50 text-black' : 'border-transparent text-gray-600 hover:bg-gray-50 hover:text-black'}">
                        <i class="fa-solid fa-box mr-3 text-sm"></i> Đơn hàng
                    </a>

                    <a href="admin?tab=products"
                       class="w-full text-left px-6 py-4 font-medium transition-colors duration-200 border-l-4
                       ${activeTab == 'products' ? 'border-[#BFA77F] bg-gray-50 text-black' : 'border-transparent text-gray-600 hover:bg-gray-50 hover:text-black'}">
                        <i class="fa-solid fa-shirt mr-3 text-sm"></i> Sản phẩm
                    </a>
                </div>
            </div>

            <div class="md:col-span-3">
                <div class="bg-white rounded-xl shadow-sm min-h-[500px] p-6">
                    
                    <c:choose>
                        
                        <%-- TAB 1: NGƯỜI DÙNG --%>
                        <c:when test="${activeTab == 'users'}">
                            <h4 class="text-2xl font-bold mb-6 pb-4 border-b">Quản lý <span class="text-[#BFA77F]">người dùng</span></h4>
                            <jsp:include page="Admin/UserManagement.jsp" />
                        </c:when>
                        
                        <%-- TAB 2: ĐƠN HÀNG --%>
                        <c:when test="${activeTab == 'orders'}">
                            <h4 class="text-2xl font-bold mb-6 pb-4 border-b">Quản lý <span class="text-[#BFA77F]">đơn hàng</span></h4>
                            <jsp:include page="Admin/OrderManagement.jsp" />
                        </c:when>
                        
                        <%-- TAB 3: SẢN PHẨM (ĐÃ GỘP TỪ ManagerProduct.jsp) --%>
                        <c:when test="${activeTab == 'products'}">
                            
                            <div class="flex justify-between items-center mb-6 pb-4 border-b">
                                <h4 class="text-2xl font-bold">Quản lý <span class="text-[#BFA77F]">sản phẩm</span></h4>
                                <button onclick="toggleModal('addProductModal')" class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg font-medium transition-colors flex items-center gap-2">
                                    <i class="fa-solid fa-plus"></i> Thêm mới
                                </button>
                            </div>

                            <div class="overflow-x-auto">
                                <table class="w-full text-left border-collapse">
                                    <thead>
                                        <tr class="text-xs font-semibold tracking-wide text-gray-500 uppercase bg-gray-50 border-b">
                                            <th class="px-4 py-3">STT</th>
                                            <th class="px-4 py-3">Ảnh</th>
                                            <th class="px-4 py-3">Tên sản phẩm</th>
                                            <th class="px-4 py-3">Giá</th>
                                            <th class="px-4 py-3 text-center">Kho</th>
                                            <th class="px-4 py-3 text-center">Đã bán</th>
                                            <th class="px-4 py-3 text-right">Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody class="divide-y divide-gray-100">
                                        
                                        <%-- SỬA 1: Thêm varStatus="loop" để lấy biến đếm --%>
                                        <c:forEach items="${listP}" var="p" varStatus="loop">
                                            
                                            <tr class="hover:bg-gray-50 transition-colors">
                                                
                                                <%-- SỬA 2: Dùng ${loop.count} để hiện số thứ tự 1, 2, 3... liên tục --%>
                                                <td class="px-4 py-3 text-gray-500 font-medium">
                                                    ${loop.count}
                                                </td>
                                                
                                                <td class="px-4 py-3">
                                                    <img src="${p.displayImage}" alt="${p.name}" class="w-12 h-12 object-cover rounded-md border">
                                                </td>
                                                <td class="px-4 py-3 font-medium text-gray-900 max-w-[200px] truncate" title="${p.name}">
                                                    ${p.name}
                                                </td>
                                                <td class="px-4 py-3 font-medium text-[#BFA77F]">
                                                    ${p.displayPriceFormatted}
                                                </td>
                                                <td class="px-4 py-3 text-center">
                                                    <span class="px-2 py-1 rounded-full text-xs font-medium ${p.stock > 10 ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}">
                                                        ${p.stock}
                                                    </span>
                                                </td>
                                                <td class="px-4 py-3 text-center text-gray-500">${p.sold}</td>
                                                <td class="px-4 py-3 text-right">
                                                    <div class="flex justify-end gap-2">
                                                        <%-- QUAN TRỌNG: Các link Sửa/Xóa bên dưới VẪN PHẢI DÙNG ${p.id} (ID thật) để biết xóa cái nào --%>
                                                        
                                                        <a href="loadProduct?pid=${p.id}" class="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors" title="Sửa">
                                                            <i class="fa-solid fa-pen-to-square"></i>
                                                        </a>
                                                        <a href="delete?pid=${p.id}" onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');" 
                                                           class="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors" title="Xóa">
                                                            <i class="fa-solid fa-trash"></i>
                                                        </a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                            <div id="addProductModal" class="modal fixed inset-0 z-50 flex items-center justify-center bg-black/50 backdrop-blur-sm">
                                <div class="modal-content bg-white w-full max-w-2xl rounded-xl shadow-2xl p-6 m-4 max-h-[90vh] overflow-y-auto">
                                    <div class="flex justify-between items-center mb-6 pb-4 border-b">
                                        <h3 class="text-xl font-bold text-gray-800">Thêm Sản Phẩm Mới</h3>
                                        <button onclick="toggleModal('addProductModal')" class="text-gray-400 hover:text-gray-600">
                                            <i class="fa-solid fa-times text-xl"></i>
                                        </button>
                                    </div>
                                    
                                    <form action="add" method="post" class="space-y-4">
                                        <div class="grid grid-cols-2 gap-4">
                                            <div class="col-span-2">
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Tên sản phẩm</label>
                                                <input name="name" type="text" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" required>
                                            </div>
                                            
                                            <div class="col-span-2">
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Hình ảnh (URL)</label>
                                                <input name="image" type="text" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" required>
                                            </div>

                                            <div>
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Giá tiền (VNĐ)</label>
                                                <input name="price" type="number" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" required>
                                            </div>
                                            <div>
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Số lượng kho</label>
                                                <input name="stock" type="number" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" value="100">
                                            </div>
                                            
                                            <div>
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Size (cách nhau bởi dấu phẩy)</label>
                                                <input name="sizes" type="text" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" placeholder="S,M,L,XL">
                                            </div>
                                            <div>
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Màu sắc (cách nhau bởi dấu phẩy)</label>
                                                <input name="colors" type="text" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" placeholder="Trắng,Đen,Xanh">
                                            </div>
                                            <div class="col-span-2">
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Chất liệu</label>
                                                <input name="material" type="text" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" placeholder="Cotton 100%, Denim...">
                                            </div>

                                            <div class="col-span-2">
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Danh mục</label>
                                                <select name="category" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent">
                                                    <option value="1">Áo Thun</option>
                                                    <option value="2">Quần Jeans</option>
                                                    <option value="3">Áo Khoác</option>
                                                    <option value="4">Đầm/Váy</option>
                                                    <option value="5">Áo Sơ Mi</option>
                                                    <option value="6">Quần Short</option>
                                                    <option value="7">Áo Len</option>
                                                </select>
                                            </div>
                                            
                                            <div class="col-span-2">
                                                <label class="block text-sm font-medium text-gray-700 mb-1">Mô tả sản phẩm</label>
                                                <textarea name="description" class="w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-black focus:border-transparent" rows="4"></textarea>
                                            </div>
                                        </div>
                                        
                                        <div class="pt-4 flex justify-end gap-3 border-t mt-4">
                                            <button type="button" onclick="toggleModal('addProductModal')" class="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-lg font-medium transition-colors">Hủy</button>
                                            <button type="submit" class="px-6 py-2 bg-green-600 hover:bg-green-700 text-white rounded-lg font-medium transition-colors">Thêm ngay</button>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </c:when>
                    </c:choose>
                    
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="Footer.jsp" />

<script>
    function toggleModal(modalID){
        const modal = document.getElementById(modalID);
        modal.classList.toggle('active');
    }
</script>

</body>
</html>