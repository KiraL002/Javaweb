<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="pt-10">
    <h4 class="text-2xl md:text-3xl font-bold mb-8">
        Quản lý <span class="text-[#BFA77F]">đơn hàng</span>
    </h4>
    <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden">
        <thead class="bg-gray-100">
        <tr>
            <th class="px-4 py-2 text-left text-gray-700">Mã đơn</th>
            <th class="px-4 py-2 text-left text-gray-700">Email Khách hàng</th>
            <th class="px-4 py-2 text-left text-gray-700">SĐT</th>
            <th class="px-4 py-2 text-left text-gray-700">Tổng cộng</th>
            <th class="px-4 py-2 text-left text-gray-700">Trạng thái</th>
            <th class="px-4 py-2 text-left text-gray-700">P.thức TT</th>
            <th class="px-4 py-2 text-left text-gray-700">Hành động</th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="o" items="${orders}" varStatus="status">
            <form action="admin/orders" method="post" class="order-form">
                <input type="hidden" name="orderId" value="${o.orderNumber}" />
                <tr class="odd:bg-gray-50 even:bg-white even:hover:bg-gray-100 odd:hover:bg-gray-100" data-order-id="${o.orderNumber}">
                    <td class="px-4 py-2">${o.orderNumber}</td>
                    <td class="px-4 py-2">
                        <span class="view">${o.userEmail}</span>
                        <input type="hidden" name="email" value="${o.userEmail}" />
                    </td>
                    <td class="px-4 py-2">
                        <span class="view">${o.phone}</span>
                        <input class="edit hidden border px-2 py-1" type="text" name="phone" value="${o.phone}" />
                    </td>
                    <td class="px-4 py-2">
                        <span class="view">
                            <fmt:formatNumber value="${o.total}" type="currency" currencyCode="VND" />
                        </span>
                        <input type="hidden" name="total" value="${o.total}" />
                    </td>
                    <td class="px-4 py-2">
                        <span class="view px-2 py-1 rounded-full text-white 
                             ${o.status == 'Đã giao' ? 'bg-green-500' : (o.status == 'Chờ xác nhận' ? 'bg-yellow-500' : 'bg-red-500') } text-sm font-semibold">
                            ${o.status}
                        </span>
                        <select class="edit hidden border px-2 py-1" name="status">
                            <option value="Chờ xác nhận" ${o.status=="Chờ xác nhận" ? 'selected' : '' }>Chờ xác nhận</option>
                            <option value="Đang vận chuyển" ${o.status=="Đang vận chuyển" ? 'selected' : '' }>Đang vận chuyển</option>
                            <option value="Đã thanh toán" ${o.status=="Đã thanh toán" ? 'selected' : '' }>Đã thanh toán</option>
                            <option value="Thanh toán thất bại" ${o.status=="Thanh toán thất bại" ? 'selected' : '' }>Thanh toán thất bại</option>
                            <option value="Đã giao" ${o.status=="Đã giao" ? 'selected' : '' }>Đã giao</option>
                        </select>
                    </td>
                    <td class="px-4 py-2">
                        <span class="view">${o.paymentMethod}</span>
                    </td>
                    <td class="px-4 py-2 flex space-x-2">
                        <span class="view">
                            <button type="button" class="edit-order-btn">
                                <i class="fa-solid fa-pen-to-square text-blue-500 hover:text-blue-700" title="Chỉnh sửa"></i>
                            </button>
                            <button type="button" class="delete-order-btn" data-id="${o.orderNumber}">
                                <i class="fa-solid fa-trash text-red-500 hover:text-red-700" title="Xóa"></i>
                            </button>
                        </span>
                        <span class="edit hidden">
                            <button type="submit" class="save-order-btn">
                                <i class="fa-solid fa-floppy-disk text-green-500 hover:text-green-700" title="Lưu"></i>
                            </button>
                            <button type="button" class="cancel-order-btn">
                                <i class="fa-solid fa-x text-black-500 hover:text-black-700" title="Hủy"></i>
                            </button>
                        </span>
                    </td>
                </tr>
            </form>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function toggleEditMode(tr, enable) {
        tr.querySelectorAll(".view").forEach(v => v.classList.toggle("hidden", enable));
        tr.querySelectorAll(".edit").forEach(e => e.classList.toggle("hidden", !enable));
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
                fetch("/admin/orders?orderNumber=" + orderNumber, { method: "DELETE" })
                    .then(response => {
                        if (response.ok) btn.closest("tr").remove();
                        else alert("Xóa đơn hàng thất bại!");
                    }).catch(err => { console.error(err); alert("Có lỗi xảy ra!"); });
            }
        });
    });
</script>
