<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="fixed top-0 left-0 right-0 z-50 bg-white shadow-sm">
    <nav class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">

            <div class="flex-shrink-0">
                <a href="home" class="text-2xl font-bold">MAU <span class="text-[#BFA77F]">Style</span></a>
            </div>

            <div class="hidden md:flex md:space-x-8">
                <a href="home" class="font-medium text-gray-700 hover:text-black">Trang chủ</a>
                <a href="shop" class="font-medium text-gray-700 hover:text-black">Sản phẩm</a>
                <a href="#footer" class="font-medium text-gray-700 hover:text-black">Về chúng tôi</a>
            </div>

            <div class="flex items-center space-x-4">

                <c:if test="${sessionScope.account == null}">
                    <a href="login" class="font-medium text-gray-700 hover:text-black">
                        Đăng nhập
                    </a>
                </c:if>

                <c:if test="${sessionScope.account != null}">
                    <div class="relative">
                        <button id="user-menu-button" class="w-10 h-10 bg-[#BFA77F] rounded-full flex items-center justify-center text-white text-lg font-bold hover:opacity-90 transition-opacity">
                                ${sessionScope.account.username.substring(0, 1).toUpperCase()}
                        </button>

                        <div id="user-menu-panel"
                             class="hidden absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-1 border border-gray-200 z-50">

                            <div class="px-4 py-2 border-b">
                                <p class="text-sm font-medium text-gray-900 truncate">${sessionScope.account.fullName}</p>
                                <p class="text-xs text-gray-500 truncate">${sessionScope.account.username}</p>
                            </div>

                            <%-- === SỬA LOGIC HIỂN THỊ MENU TẠI ĐÂY === --%>
                            <c:choose>
                                <%-- TRƯỜNG HỢP 1: TÀI KHOẢN LÀ ADMIN --%>
                                <c:when test="${sessionScope.account.role == 'ADMIN'}">
                                    <%-- Chỉ hiện nút Quản lý (đã gộp quản lý sản phẩm vào trong trang admin) --%>
                                    <a href="admin" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                        Quản lý
                                    </a>
                                </c:when>
                                
                                <%-- TRƯỜNG HỢP 2: TÀI KHOẢN NGƯỜI DÙNG THƯỜNG --%>
                                <c:otherwise>
                                    <%-- Chỉ hiện thông tin cá nhân --%>
                                    <a href="account?tab=profile" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                        Thông tin cá nhân
                                    </a>
                                </c:otherwise>
                            </c:choose>
                            
                            <%-- Nút Đăng xuất (Luôn hiển thị) --%>
                            <a href="logout" class="block px-4 py-2 text-sm text-red-600 hover:bg-red-50 border-t border-gray-200">
                                Đăng xuất
                            </a>
                            
                        </div>
                    </div>
                </c:if>

                <%-- SỬA: Chỉ hiển thị Giỏ hàng nếu KHÔNG PHẢI là ADMIN --%>
                <c:if test="${sessionScope.account == null || sessionScope.account.role != 'ADMIN'}">
                    
                    <div class="border-l border-gray-200 pl-4">
                        <a href="cart" class="text-gray-600 hover:text-black">
                            <svg class="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.993 1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 0 
    1-1.12-1.243l1.264-12A1.125 1.125 0 0 1 5.513 7.5h12.974c.576 0 1.059.435 1.119 1.007Z" />
                            </svg>
                        </a>
                    </div>
                    
                </c:if>
            </div>

        </div>
    </nav>

    <script>
        if (typeof menuDropdownInitialized === 'undefined') {
            const menuDropdownInitialized = true;
            document.addEventListener('click', function(event) {
                const button = document.getElementById('user-menu-button');
                const panel = document.getElementById('user-menu-panel');
                if (!button || !panel) {
                    return;
                }
                if (button.contains(event.target)) {
                    panel.classList.toggle('hidden');
                } else if (!panel.contains(event.target)) {
                    panel.classList.add('hidden');
                }
            });
        }
    </script>
</header>