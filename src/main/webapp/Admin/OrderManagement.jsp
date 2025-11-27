<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>


<body class="bg-[#FAFAF9]">

<div>
    <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden">

        <thead class="bg-gray-100">
        <tr>
            <th class="px-4 py-2 text-left text-gray-700">Ngày đặt</th>
            <th class="px-4 py-2 text-left text-gray-700">Khách hàng</th>
            <th class="px-4 py-2 text-left text-gray-700">Tổng tiền</th>
            <th class="px-4 py-2 text-left text-gray-700">Trạng thái đơn</th>
            <th class="px-4 py-2 text-left text-gray-700">Phương thức thanh toán</th>
            <th class="px-4 py-2 text-left text-gray-700">Chi tiết</th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="o" items="${orders}">
                <tr data-id="${o.userId}"
                    class="odd:bg-gray-50 even:bg-white even:hover:bg-gray-100 odd:hover:bg-gray-100">

                    <td class="px-4 py-2">
                        <fmt:formatDate value="${o.createdDate}" pattern="HH:mm dd/MM/yyyy" />
                    </td>

                    <td class="px-4 py-2">
                        ${o.userName}
                    </td>

                    <td class="px-4 py-2">
                       <fmt:formatNumber value="${o.total}" type="number" groupingUsed="true"/> <u>đ</u>
                       
                    </td>
                    
                    <td class="px-4 py-2">
                        <form action="admin/order" method="post">
                             <input type="hidden" name="orderId" value="${o.orderId}" />     
                             <select class="border px-2 py-1 " name="status"onchange="this.form.submit()">
                                 <option value="PENDING" ${o.status=='PENDING' ? 'selected' : ''}>Chờ xác nhận</option>
                                 <option value="SHIPPED" ${o.status=='SHIPPED' ? 'selected' : ''}>Đang giao</option>
                                 <option value="DELIVERED" ${o.status=='DELIVERED' ? 'selected' : ''}>Giao thành công</option>
                                 <option value="CANCELLED" ${o.status=='CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                                 <option value="RETURNED" ${o.status=='RETURNED' ? 'selected' : ''}>Trả hàng hoàn tiền</option>
                             </select>
                         </form>
                    </td>
                    <td class="px-4 py-2">
                        <form action="admin/order" method="post">
                            <input type="hidden" name="orderId" value="${o.orderId}"/>
                            <select class="border px-2 py-1" name="paymentMethod"onchange="this.form.submit()">
                                <option value="CASH" ${o.paymentMethod=="CASH" ? 'selected' : '' }>Thanh toán khi nhận hàng</option>
                                <option value="TRANSFER" ${o.paymentMethod=="TRANSFER" ? 'selected' : '' }>Chuyển khoản</option>
                            </select>
                        </form>
                    </td>

                    <td class="px-4 py-2 text-center ">
                        <button type="button" class="px-3 py-1">
                            <a href="admin/order/${o.orderId}">Xem</a></button>
                    </td>


                </tr>
            
        </c:forEach>

        </tbody>
    </table>

</div>

    <script>





    </script>
</body>