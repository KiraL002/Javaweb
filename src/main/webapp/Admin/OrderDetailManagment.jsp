<%--
    Document : OrderDetailManagment
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thông tin chi tiết đơn hàng</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <jsp:include page="/Menu.jsp" />
        <div class="min-h-screen pt-20 p-8">
            
            <h2 class="text-2xl font-semibold mb-4">Thông tin chi tiết đơn hàng</h2>
            <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden mb-8">
                <tbody class="bg-white divide-y divide-gray-100">
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">Mã đơn hàng:</td>
                        <td class="px-4 py-2">${order.orderId}</td>
                    </tr>
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">Ngày đặt:</td>
                        <td class="px-4 py-2"><fmt:formatDate value="${order.createdDate}" pattern="HH:mm dd/MM/yyyy" /></td>
                    </tr>
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">Khách hàng:</td>
                        <td class="px-4 py-2">${order.userName} (${order.userEmail})</td>
                    </tr>
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">Địa chỉ giao hàng:</td>
                        <td class="px-4 py-2">${order.shippingAddress}</td>
                    </tr>
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">SĐT:</td>
                        <td class="px-4 py-2">${order.phone}</td>
                    </tr>
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">Phương thức thanh toán:</td>
                        <td class="px-4 py-2">${order.paymentMethod}</td>
                    </tr>
                    <tr>
                        <td class="px-4 py-2 font-semibold bg-gray-50 w-1/4">Trạng thái:</td>
                        <td class="px-4 py-2 font-bold">${order.status}</td>
                    </tr>
                </tbody>
            </table>

            <h2 class="text-2xl font-semibold mb-4">Chi tiết sản phẩm</h2>
            <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden mt-4">
                <thead class="bg-gray-100">
                    <tr>
                        <th class="px-4 py-2 text-left">STT</th>
                        <th class="px-4 py-2 text-left">Tên sản phẩm (Size/Màu)</th>
                        <th class="px-4 py-2 text-left">Số lượng</th>
                        <th class="px-4 py-2 text-right">Đơn giá</th>
                        <th class="px-4 py-2 text-right">Thành tiền</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <c:forEach var="item" items="${items}" varStatus="status">
                        <tr>
                            <td class="px-4 py-2">${status.index + 1}</td>
                            <td class="px-4 py-2">
                                ${item.productName} 
                                <span class="text-gray-500 text-sm">(${item.size} / ${item.color})</span>
                            </td>
                            <td class="px-4 py-2">${item.quantity}</td>
                            
                            <td class="px-4 py-2 text-right">
                                <fmt:formatNumber value="${item.unitPrice}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                            </td>
                            
                            <td class="px-4 py-2 text-right font-semibold">
                                <fmt:formatNumber value="${item.unitPrice * item.quantity}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <tr class="font-bold bg-gray-50">
                        <td colspan="4" class="px-4 py-2 text-right">Tạm tính:</td>
                        <td class="px-4 py-2 text-right">
                            <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </td>
                    </tr>
                    <tr class="font-bold bg-gray-50">
                        <td colspan="4" class="px-4 py-2 text-right">Phí vận chuyển:</td>
                        <td class="px-4 py-2 text-right">
                            <fmt:formatNumber value="${order.shipping}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </td>
                    </tr>
                    <tr class="font-bold bg-gray-200 text-lg">
                        <td colspan="4" class="px-4 py-2 text-right">Tổng cộng:</td>
                        <td class="px-4 py-2 text-right text-red-600">
                            <fmt:formatNumber value="${order.total}" type="currency" currencySymbol="₫" maxFractionDigits="0" />
                        </td>
                    </tr>
                </tbody>
            </table>
            
        </div>
        <jsp:include page="/Footer.jsp" />
    </body>
</html>