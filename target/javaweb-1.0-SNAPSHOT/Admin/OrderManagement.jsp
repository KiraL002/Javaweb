<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>


<body class="bg-[#FAFAF9]">

<div>
    <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden">

        <thead class="bg-gray-100">
        <tr>
            <th class="px-4 py-2 text-left text-gray-700">Mã đơn</th>
            <th class="px-4 py-2 text-left text-gray-700">Khách hàng</th>
            <th class="px-4 py-2 text-left text-gray-700">Tổng tiền</th>
            <th class="px-4 py-2 text-left text-gray-700">Trạng thái đơn</th>
            <th class="px-4 py-2 text-left text-gray-700">Thanh toán</th>
            <th class="px-4 py-2 text-left text-gray-700">Chi tiết</th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
<%--        <c:forEach var="o" items="${orders}" varStatus="status">--%>

<%--            <form action="admin/users" method="post">--%>
<%--                <input type="hidden" name="userId" value="${o.orderNumber}" />--%>

<%--                <tr data-id="${o.userId}"--%>
<%--                    class="odd:bg-gray-50 even:bg-white even:hover:bg-gray-100 odd:hover:bg-gray-100">--%>

<%--                    <td class="px-4 py-2">${status.index+1}</td>--%>

<%--                    <td class="px-4 py-2">--%>
<%--                        <span class="view" data-field="username">${o.username}</span>--%>
<%--                        <input class="edit hidden border px-2 py-1" type="text" name="username"--%>
<%--                               value="${o.username}" />--%>
<%--                    </td>--%>

<%--                    <td class="px-4 py-2">--%>
<%--                        <span class="view" data-field="phone">${o.phone}</span>--%>
<%--                        <input class="edit hidden border px-2 py-1" type="text" name="phone" value="${o.phone}" />--%>
<%--                    </td>--%>

<%--                    <td class="px-4 py-2">--%>
<%--                        <span class="view" data-field="role">${o.role}</span>--%>
<%--                        <select class="edit hidden border px-2 py-1" name="role">--%>
<%--                            <option value="USER" ${o.role=="USER" ? 'selected' : '' }>USER</option>--%>
<%--                            <option value="ADMIN" ${o.role=="ADMIN" ? 'selected' : '' }>ADMIN</option>--%>
<%--                            <option value="STAFF" ${o.role=="STAFF" ? 'selected' : '' }>STAFF</option>--%>
<%--                        </select>--%>
<%--                    </td>--%>

<%--                    <td class="px-4 py-2">--%>
<%--                <span class="view px-2 py-1 rounded-full text-white--%>
<%--                      ${o.status == 'ACTIVE' ? 'bg-green-500' : 'bg-red-500' } text-sm font-semibold" name="status">--%>
<%--                        ${o.status}--%>
<%--                </span>--%>
<%--                        <select class="edit hidden border px-2 py-1" name="status">--%>
<%--                            <option value="ACTIVE" ${o.status=="ACTIVE" ? 'selected' : '' }>ACTIVE</option>--%>
<%--                            <option value="SUSPENDED" ${o.status=="SUSPENDED" ? 'selected' : '' }>SUSPENDED</option>--%>
<%--                        </select>--%>
<%--                    </td>--%>

<%--                    <td class="px-4 py-2 flex space-x-2">--%>

<%--                    <span class="view">--%>
<%--                        <button type="button">--%>
<%--                            <i class="edit-btn fa-solid fa-pen-to-square text-blue-500 hover:text-blue-700"--%>
<%--                               title="Chỉnh sửa"></i>--%>
<%--                        </button>--%>

<%--                        <button type="button">--%>
<%--                            <i class="fa-solid fa-trash text-red-500 hover:text-red-700"--%>
<%--                               title="Xóa vĩnh viễn"></i>--%>
<%--                        </button>--%>
<%--                    </span>--%>

<%--                        <span class="edit hidden">--%>
<%--                        <button type="submit">--%>
<%--                            <i class="save-btn fa-solid fa-floppy-disk text-green-500 hover:text-green-700"--%>
<%--                               title="Lưu"></i>--%>
<%--                        </button>--%>
<%--                        <button type="button">--%>
<%--                            <i class="cancel-btn fa-solid fa-x text-black-500 hover:text-black-700"--%>
<%--                               title="Hủy"></i>--%>
<%--                        </button>--%>
<%--                    </span>--%>
<%--                    </td>--%>

<%--                </tr>--%>
<%--            </form>--%>
<%--        </c:forEach>--%>

        </tbody>
    </table>

</div>

</body>