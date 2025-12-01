<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="pt-6">
    <div class="flex justify-between items-end mb-6">
        <div class="text-sm text-gray-500">Tổng số: <span class="font-semibold text-gray-900">${orders.size()}</span> đơn</div>
    </div>

    <div class="overflow-x-auto bg-white rounded-xl border border-gray-200 shadow-sm">
        <table class="w-full text-left border-collapse">
            <thead>
                <tr class="text-xs font-semibold tracking-wide text-gray-500 uppercase bg-gray-50 border-b border-gray-200">
                    <th class="px-6 py-4 w-24">Mã đơn</th>
                    <th class="px-6 py-4 w-48">Khách hàng</th>
                    <th class="px-6 py-4 w-32">SĐT</th>
                    <th class="px-6 py-4 w-32">Tổng tiền</th>
                    <th class="px-6 py-4 w-40">Trạng thái</th>
                    <th class="px-6 py-4 w-40">Thanh toán</th>
                    <th class="px-6 py-4 text-right w-32">Hành động</th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-100">
                <c:forEach var="o" items="${orders}" varStatus="status">
                    <form action="admin/orders" method="post" class="order-form">
                        <input type="hidden" name="orderId" value="${o.orderNumber}" />
                        
                        <tr class="hover:bg-gray-50/80 transition-colors group" data-order-id="${o.orderNumber}">
                            <td class="px-6 py-4 text-sm font-medium text-gray-900">
                                #${o.orderNumber}
                            </td>

                            <td class="px-6 py-4">
                                <div class="flex flex-col">
                                    <span class="text-sm font-medium text-gray-900 truncate max-w-[150px]" title="${o.userEmail}">${o.userEmail}</span>
                                    <input type="hidden" name="email" value="${o.userEmail}" />
                                </div>
                            </td>

                            <td class="px-6 py-4 text-sm text-gray-500">
                                <span class="view">${o.phone}</span>
                                <input class="edit hidden w-full px-2 py-1 text-sm border border-gray-300 rounded focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none" type="text" name="phone" value="${o.phone}" />
                            </td>

                            <td class="px-6 py-4 text-sm font-semibold text-gray-900">
                                <span class="view">
                                    <fmt:formatNumber value="${o.total}" type="currency" currencyCode="VND" />
                                </span>
                                <input type="hidden" name="total" value="${o.total}" />
                            </td>

                            <td class="px-6 py-4">
                                <div class="view">
                                    <span class="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium border
                                        <c:choose>
                                            <c:when test="${o.status == 'Đã giao' || o.status == 'Hoàn thành'}">bg-green-50 text-green-700 border-green-100</c:when>
                                            <c:when test="${o.status == 'Chờ xác nhận'}">bg-yellow-50 text-yellow-700 border-yellow-100</c:when>
                                            <c:when test="${o.status == 'Đã hủy'}">bg-red-50 text-red-700 border-red-100</c:when>
                                            <c:when test="${o.status == 'Đang giao'}">bg-blue-50 text-blue-700 border-blue-100</c:when>
                                            <c:otherwise>bg-gray-50 text-gray-700 border-gray-100</c:otherwise>
                                        </c:choose>
                                    ">
                                        ${o.status}
                                    </span>
                                </div>
                                <select class="edit hidden w-full px-2 py-1 text-sm border border-gray-300 rounded bg-white focus:ring-2 focus:ring-blue-500 outline-none" name="status">
                                    <option value="Chờ thanh toán" ${o.status=="Chờ thanh toán" ? 'selected' : '' }>Chờ thanh toán</option>
                                    <option value="Chờ xác nhận" ${o.status=="Chờ xác nhận" ? 'selected' : '' }>Chờ xác nhận</option>
                                    <option value="Đang vận chuyển" ${o.status=="Đang vận chuyển" ? 'selected' : '' }>Đang vận chuyển</option>
                                    <option value="Đã thanh toán" ${o.status=="Đã thanh toán" ? 'selected' : '' }>Đã thanh toán</option>
                                    <option value="Thanh toán thất bại" ${o.status=="Thanh toán thất bại" ? 'selected' : '' }>Thanh toán thất bại</option>
                                    <option value="Đã giao" ${o.status=="Đã giao" ? 'selected' : '' }>Đã giao</option>
                                </select>
                            </td>

                            <td class="px-6 py-4 text-sm text-gray-500">
                                <span class="view px-2 py-1 bg-gray-100 rounded text-xs font-medium text-gray-600">${o.paymentMethod}</span>
                            </td>

                            <td class="px-6 py-4 text-right">
                                <div class="view flex justify-end items-center gap-2">
                                    <button type="button" class="edit-order-btn p-1.5 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors" title="Chỉnh sửa">
                                        <i class="fa-solid fa-pen-to-square"></i>
                                    </button>
                                    <button type="button" class="delete-order-btn p-1.5 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-lg transition-colors" data-id="${o.orderNumber}" title="Xóa">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                                <div class="edit hidden flex justify-end items-center gap-2">
                                    <button type="submit" class="save-order-btn p-1.5 text-green-600 hover:bg-green-50 rounded-lg transition-colors" title="Lưu">
                                        <i class="fa-solid fa-check"></i>
                                    </button>
                                    <button type="button" class="cancel-order-btn p-1.5 text-gray-400 hover:text-gray-600 hover:bg-gray-100 rounded-lg transition-colors" title="Hủy">
                                        <i class="fa-solid fa-xmark"></i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function toggleEditMode(tr, enable) {
        tr.querySelectorAll(".view").forEach(v => v.classList.toggle("hidden", enable));
        tr.querySelectorAll(".edit").forEach(e => e.classList.toggle("hidden", !enable));
        
        // Thêm background highlight khi đang edit
        if (enable) tr.classList.add("bg-blue-50/50");
        else tr.classList.remove("bg-blue-50/50");
    }
    
    document.querySelectorAll(".edit-order-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const tr = btn.closest("tr");
            toggleEditMode(tr, true);
        });
    });
    
    document.querySelectorAll(".cancel-order-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const tr = btn.closest("tr");
            toggleEditMode(tr, false);
        });
    });
    
    document.querySelectorAll(".delete-order-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const orderNumber = btn.getAttribute("data-id");
            if (confirm("Bạn có chắc muốn xóa đơn hàng " + orderNumber + " này không?")) {
                fetch("${pageContext.request.contextPath}/admin/orders?orderId=" + orderNumber, { method: "DELETE" })
                    .then(response => {
                        if (response.ok) {
                            const tr = btn.closest("tr");
                            tr.style.transition = "all 0.3s ease";
                            tr.style.opacity = "0";
                            setTimeout(() => tr.remove(), 300);
                        }
                        else alert("Xóa đơn hàng thất bại!");
                    }).catch(err => { console.error(err); alert("Có lỗi xảy ra!"); });
            }
        });
    });
</script>
