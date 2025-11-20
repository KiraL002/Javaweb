<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tài khoản - MAU Style</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        body {
            font-family: 'Inter', sans-serif;
        }
    </style>
</head>

<body class="bg-[#FAFAF9]">

<div>
    <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden">

        <thead class="bg-gray-100">
        <tr>
            <th class="px-4 py-2 text-left text-gray-700">STT</th>
            <th class="px-4 py-2 text-left text-gray-700">Tên tài khoản</th>
            <th class="px-4 py-2 text-left text-gray-700">SĐT</th>
            <th class="px-4 py-2 text-left text-gray-700">Vai trò</th>
            <th class="px-4 py-2 text-left text-gray-700">Trạng thái</th>
            <th class="px-4 py-2 text-left text-gray-700">Hành động</th>
        </tr>
        </thead>

        <tbody class="bg-white divide-y divide-gray-200">
        <c:forEach var="u" items="${users}" varStatus="status">

            <form action="admin/users" method="post">
                <input type="hidden" name="userId" value="${u.userId}" />

                <tr data-id="${u.userId}"
                    class="odd:bg-gray-50 even:bg-white even:hover:bg-gray-100 odd:hover:bg-gray-100">

                    <td class="px-4 py-2">${status.index+1}</td>

                    <td class="px-4 py-2">
                        <span class="view" data-field="username">${u.username}</span>
                        <input class="edit hidden border px-2 py-1" type="text" name="username"
                               value="${u.username}" />
                    </td>

                    <td class="px-4 py-2">
                        <span class="view" data-field="phone">${u.phone}</span>
                        <input class="edit hidden border px-2 py-1" type="text" name="phone" value="${u.phone}" />
                    </td>

                    <td class="px-4 py-2">
                        <span class="view" data-field="role">${u.role}</span>
                        <select class="edit hidden border px-2 py-1" name="role">
                            <option value="USER" ${u.role=="USER" ? 'selected' : '' }>USER</option>
                            <option value="ADMIN" ${u.role=="ADMIN" ? 'selected' : '' }>ADMIN</option>
                        </select>
                    </td>

                    <td class="px-4 py-2">
                        <span class="view px-2 py-1 rounded-full text-white
                              ${u.status == 'ACTIVE' ? 'bg-green-500' : 'bg-red-500' } text-sm font-semibold" name="status">
                                ${u.status}
                        </span>
                        <select class="edit hidden border px-2 py-1" name="status">
                            <option value="ACTIVE" ${u.status=="ACTIVE" ? 'selected' : '' }>ACTIVE</option>
                            <option value="SUSPENDED" ${u.status=="SUSPENDED" ? 'selected' : '' }>SUSPENDED</option>
                        </select>
                    </td>

                    <td class="px-4 py-2 flex space-x-2">

                            <span class="view">
                                <button type="button">
                                    <i class="edit-btn fa-solid fa-pen-to-square text-blue-500 hover:text-blue-700"
                                       title="Chỉnh sửa"></i>
                                </button>

                                <button type="button">
                                    <i class="fa-solid fa-trash text-red-500 hover:text-red-700"
                                       title="Xóa vĩnh viễn"></i>
                                </button>
                            </span>

                        <span class="edit hidden">
                                <button type="submit">
                                    <i class="save-btn fa-solid fa-floppy-disk text-green-500 hover:text-green-700"
                                       title="Lưu"></i>
                                </button>
                                <button type="button">
                                    <i class="cancel-btn fa-solid fa-x text-black-500 hover:text-black-700"
                                       title="Hủy"></i>
                                </button>
                            </span>
                    </td>

                </tr>
            </form>
        </c:forEach>
        </tbody>
    </table>

</div>

</body>

</html>