<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%--
Đây là tệp Footer (Chân trang), dựa trên Layout.jsx
Nó sẽ được nhúng vào các trang khác (Home.jsp, Shop.jsp, v.v.)
--%>
<footer id="footer" class="bg-black text-white mt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 md:gap-12">
            <!-- Column 1 -->
            <div>
                <h3 class="text-2xl font-bold mb-4">
                    MAU <span class="text-[#BFA77F]">STYLE</span>
                </h3>
                <p class="text-gray-400 text-sm leading-relaxed">
                    Simple Fit – Simple Life.<br />
                    Thời trang tối giản cho giới trẻ.<br />
                    Mặc đẹp không cần phức tạp.
                </p>
                <div class="flex gap-4 mt-6">
                    <a href="#" class="w-10 h-10 bg-white/10 hover:bg-[#BFA77F] rounded-full flex items-center justify-center transition-colors">
                        <%-- Icon Instagram --%>
                        <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="2" y="2" width="20" height="20" rx="5" ry="5"></rect>
                            <path d="M16 11.37A4 4 0 1 1 12.63 8 4 4 0 0 1 16 11.37z"></path>
                            <line x1="17.5" y1="6.5" x2="17.51" y2="6.5"></line>
                        </svg>
                    </a>
                    <a href="#" class="w-10 h-10 bg-white/10 hover:bg-[#BFA77F] rounded-full flex items-center justify-center transition-colors">
                        <%-- Icon Facebook --%>
                        <svg class="w-5 h-5" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M18 2h-3a5 5 0 0 0-5 5v3H7v4h3v8h4v-8h3l1-4h-4V7a1 1 0 0 1 1-1h3z"></path>
                        </svg>
                    </a>
                </div>
            </div>

            <!-- Column 2 -->
            <div>
                <h4 class="font-semibold text-lg mb-4">Liên hệ</h4>
                <ul class="space-y-2 text-sm text-gray-400">
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Zalo: 0123 456 789</li>
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Facebook: @maustyle</li>
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Instagram: @mau.style</li>
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Shopee: MAU STYLE Official</li>
                </ul>
            </div>

            <!-- Column 3 -->
            <div>
                <h4 class="font-semibold text-lg mb-4">Chính sách</h4>
                <ul class="space-y-2 text-sm text-gray-400">
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Chính sách giao hàng</li>
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Chính sách đổi trả</li>
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Hướng dẫn chọn size</li>
                    <li class="hover:text-[#BFA77F] cursor-pointer transition-colors">Bảo mật thông tin</li>
                </ul>
            </div>
        </div>

        <div class="border-t border-white/10 mt-12 pt-8 text-center text-sm text-gray-500">
            © 2025 MAU STYLE. All rights reserved. Built with love for simplicity.
        </div>
    </div>
</footer>
