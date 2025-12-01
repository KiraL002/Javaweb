<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "jakarta.tags.core" %>
<%@ taglib prefix = "fmt" uri = "jakarta.tags.fmt" %>
<%@ taglib prefix = "mytag" tagdir = "/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tài khoản - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    
    <link rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" 
          crossorigin="anonymous" referrerpolicy="no-referrer" />
          
    <style>
        body { font-family: 'Inter', sans-serif; }
    </style>
</head>
<body class="bg-[#FAFAF9]">

    <jsp:include page="Menu.jsp" />

    <div class="min-h-screen pt-20">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <h1 class="text-3xl md:text-4xl font-bold mb-8">
                Tài khoản <span class="text-[#BFA77F]">của bạn</span>
            </h1>

            <div class="grid md:grid-cols-3 gap-8">
                <div class="md:col-span-1">
                    <div class="bg-white rounded-xl p-6 shadow-sm sticky top-24">
                        <c:if test="${sessionScope.account != null}">
                            <div class="flex items-center gap-4 mb-6 pb-6 border-b">
                                <div class="w-16 h-16 bg-[#BFA77F] rounded-full flex items-center justify-center text-white text-2xl font-bold">
                                    ${sessionScope.account.username.substring(0, 1).toUpperCase()}
                                </div>
                                <div>
                                    <h3 class="font-semibold text-lg">${sessionScope.account.fullName}</h3>
                                    <p class="text-sm text-gray-600">${sessionScope.account.username}</p>
                                </div>
                            </div>
                        </c:if>

                        <nav class="space-y-2">
                            <a href="account?tab=orders" 
                               class="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors
                                            ${activeTab == 'orders' ? 'bg-gray-100 font-medium' : 'text-gray-600 hover:bg-gray-50'}">
                                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                      <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.5 2.13 2.5a3.864 3.864 0 0 1 1.055 3.197 1.578 1.578 0 0 0 .101.583v1.954a3 3 0 0 1-3 3H3.257a3 3 0 0 1-3-3v-1.954a1.58 1.58 0 0 0 .101-.583 3.864 3.864 0 0 1 1.055-3.197l2.129-2.5a.75.75 0 0 1 .447-.282H15.308a.75.75 0 0 1 .448.282Z" />
                                   </svg>
                                Đơn hàng của tôi
                            </a>
                            <a href="account?tab=profile" 
                               class="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors
                                            ${activeTab == 'profile' ? 'bg-gray-100 font-medium' : 'text-gray-600 hover:bg-gray-50'}">
                                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                      <path stroke-linecap="round" stroke-linejoin="round" d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a11.986 11.986 0 0 1-1.396 1.637 11.986 11.986 0 0 1-2.91 1.393M12 12.75a3.75 3.75 0 1 0 0-7.5 3.75 3.75 0 0 0 0 7.5Z" />
                                    </svg>
                                Thông tin cá nhân
                            </a>
                        </nav>
                        <div class="mt-6 pt-6 border-t">
                            <a href="logout" class="w-full flex items-center gap-3 px-4 py-3 rounded-lg text-red-600 hover:bg-red-50 transition-colors">
                                <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                      <path stroke-linecap="round" stroke-linejoin="round" d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a11.986 11.986 0 0 1-1.396 1.637 11.986 11.986 0 0 1-2.91 1.393m0-12A4.5 4.5 0 0 1 12 12.75V12a4.5 4.5 0 1 0 0-9 4.5 4.5 0 0 0 0 9Z" />
                                    </svg>
                                Đăng xuất
                            </a>
                        </div>
                    </div>
                </div>

                <div class="md:col-span-2">
                    <div class="bg-white rounded-xl p-6 sm:p-8 shadow-sm">
                        
                        <%-- KHỐI 1: TAB SWITCHER (ĐÃ NÉN JSTL) --%>
                        <c:choose><c:when test="${activeTab == 'orders'}">
                            <h2 class="text-2xl font-bold mb-6">Đơn hàng của tôi</h2>

                            <%-- KHỐI 2: KIỂM TRA ĐƠN HÀNG (ĐÃ NÉN JSTL) --%>
                            <c:choose><c:when test="${empty userOrders}">
                                <div class="text-center py-12">
                                    <svg class="w-16 h-16 mx-auto mb-4 text-gray-300" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.5 2.13 2.5a3.864 3.864 0 0 1 1.055 3.197 1.578 1.578 0 0 0 .101.583v1.954a3 3 0 0 1-3 3H3.257a3 3 0 0 1-3-3v-1.954a1.58 1.58 0 0 0 .101-.583 3.864 3.864 0 0 1 1.055-3.197l2.129-2.5a.75.75 0 0 1 .447-.282H15.308a.75.75 0 0 1 .448.282Z" />
                                        </svg>
                                    <h3 class="text-xl font-semibold text-gray-800 mb-2">Chưa có đơn hàng</h3>
                                    <p class="text-gray-500 mb-6">Bạn chưa có đơn hàng nào</p>
                                    <a href="shop" class="inline-block bg-black text-white px-6 py-3 rounded-lg font-semibold hover:bg-gray-800">
                                        Mua sắm ngay
                                    </a>
                                </div>
                            </c:when><c:otherwise>
                                <div class="overflow-x-auto rounded-lg border border-gray-100">
                                    <table class="w-full text-left border-collapse bg-white">
                                        <thead>
                                            <tr class="text-xs font-semibold tracking-wide text-gray-500 uppercase border-b border-gray-100 bg-gray-50/50">
                                                <th class="px-6 py-4">Mã đơn</th>
                                                <th class="px-6 py-4">Ngày đặt</th>
                                                <th class="px-6 py-4">Tổng tiền</th>
                                                <th class="px-6 py-4">Trạng thái</th>
                                                <th class="px-6 py-4 text-right">Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody class="divide-y divide-gray-50">
                                            <c:forEach var="o" items="${userOrders}">
                                                <tr class="hover:bg-gray-50 transition-colors group">
                                                    <td class="px-6 py-4 font-medium text-gray-900 group-hover:text-black">
                                                        #${o.orderNumber}
                                                    </td>
                                                    <td class="px-6 py-4 text-sm text-gray-500">
                                                        <fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy HH:mm" />
                                                    </td>
                                                    <td class="px-6 py-4 font-semibold text-gray-900">
                                                        <fmt:formatNumber value="${o.total}" type="currency" currencyCode="VND"/>
                                                    </td>
                                                    <td class="px-6 py-4">
                                                        <span class="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium border
                                                            <c:choose>
                                                                <c:when test="${o.status == 'Đã giao' || o.status == 'Hoàn thành'}">bg-green-50 text-green-700 border-green-100</c:when>
                                                                <c:when test="${o.status == 'Chờ xác nhận' || o.status == 'PENDING'}">bg-yellow-50 text-yellow-700 border-yellow-100</c:when>
                                                                <c:when test="${o.status == 'Đã hủy' || o.status == 'Cancelled'}">bg-red-50 text-red-700 border-red-100</c:when>
                                                                <c:when test="${o.status == 'Đang giao' || o.status == 'SHIPPED'}">bg-blue-50 text-blue-700 border-blue-100</c:when>
                                                                <c:otherwise>bg-gray-50 text-gray-700 border-gray-100</c:otherwise>
                                                            </c:choose>
                                                        ">
                                                            <span class="w-1.5 h-1.5 rounded-full mr-1.5 
                                                                <c:choose>
                                                                    <c:when test="${o.status == 'Đã giao'}">bg-green-500</c:when>
                                                                    <c:when test="${o.status == 'Chờ xác nhận'}">bg-yellow-500</c:when>
                                                                    <c:when test="${o.status == 'Đã hủy'}">bg-red-500</c:when>
                                                                    <c:when test="${o.status == 'Đang giao'}">bg-blue-500</c:when>
                                                                    <c:otherwise>bg-gray-500</c:otherwise>
                                                                </c:choose>
                                                            "></span>
                                                            ${o.status}
                                                        </span>
                                                    </td>
                                                    <td class="px-6 py-4 text-right">
                                                        <div class="flex justify-end items-center gap-2">
                                                            
                                                            <%-- Nút Hủy Đơn --%>
                                                            <c:if test="${o.status eq 'Chờ xác nhận' || o.status eq 'PENDING'||o.status eq 'Chờ thanh toán'}">
                                                                <button type="button"
                                                                        class="cancel-order-btn inline-flex items-center gap-1.5 text-sm font-medium text-red-600 hover:text-red-700 hover:bg-red-50 px-3 py-1.5 rounded-lg transition-all"
                                                                        data-id="${o.orderNumber}">
                                                                    <i class="fa-solid fa-ban"></i>
                                                                    Hủy
                                                                </button>
                                                            </c:if>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise></c:choose>
                        </c:when>
                        
                        <%-- TAB THÔNG TIN CÁ NHÂN (ĐÃ NÉN JSTL) --%>
                        <c:when test="${activeTab == 'profile'}">
                            
                            <h2 class="text-2xl font-bold mb-6">Thông tin cá nhân</h2>
                            
                            <c:if test="${param.update == 'success'}">
                                <div class="mb-4 p-4 bg-green-100 text-green-700 rounded-lg text-center">Cập nhật thông tin thành công!</div>
                            </c:if>

                            <form action="update-profile" method="POST" class="space-y-5">
                                <input type="hidden" name="action" value="updateInfo">
                                
                                <div>
                                    <label for="username" class="block text-sm font-medium text-gray-700 mb-1">Tên đăng nhập</label>
                                    <input type="text" id="username" name="username" value="${sessionScope.account.username}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg bg-gray-100 cursor-not-allowed" 
                                           readonly>
                                </div>
                                <div>
                                    <label for="fullName" class="block text-sm font-medium text-gray-700 mb-1">Họ và tên</label>
                                    <input type="text" id="fullName" name="fullName" value="${sessionScope.account.fullName}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black" required>
                                </div>
                                <div>
                                    <label for="phone" class="block text-sm font-medium text-gray-700 mb-1">Số điện thoại</label>
                                    <input type="tel" id="phone" name="phone" value="${sessionScope.account.phone}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black" required>
                                </div>
                                <div>
                                    <label for="address" class="block text-sm font-medium text-gray-700 mb-1">Địa chỉ</label>
                                    <input type="text" id="address" name="address" value="${sessionScope.account.address}"
                                           class="w-full px-4 py-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-black">
                                </div>
                                <div class="text-right pt-2">
                                    <button type="submit" class="bg-black text-white px-6 py-3 rounded-lg font-semibold hover:bg-gray-800 transition-colors">
                                        Lưu thay đổi
                                    </button>
                                </div>
                            </form>
                        </c:when></c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="Footer.jsp" />

</body>
<script>
// Logic xử lý HỦY đơn hàng bằng AJAX
document.querySelectorAll(".cancel-order-btn").forEach(btn => {
    btn.addEventListener("click", () => {
        const orderNumber = btn.getAttribute("data-id");
        
        if (confirm("Bạn có chắc muốn hủy đơn hàng " + orderNumber + " này không?")) {
            // Gọi Servlet AccountControl (giả sử nó ánh xạ tới "/account" và xử lý DELETE request)
            fetch("account?orderId=" + orderNumber, { method: "DELETE" }) 
                .then(response => {
                    if (response.ok) {
                        alert("Đơn hàng " + orderNumber + " đã được hủy thành công!");
                        window.location.reload(); // Tải lại trang để cập nhật trạng thái
                    }
                    else {
                        // Thử lấy thông báo lỗi từ server nếu có
                        response.text().then(text => {
                             alert("Hủy đơn hàng thất bại. Lỗi: " + text);
                        }).catch(() => {
                             alert("Hủy đơn hàng thất bại!");
                        });
                    }
                })
                .catch(err => { console.error(err); alert("Có lỗi xảy ra khi kết nối!"); });
        }
    });
});
</script>
</html>
