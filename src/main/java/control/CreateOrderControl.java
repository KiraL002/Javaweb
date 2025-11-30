package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreateOrderControl", urlPatterns = {"/create-order"})
public class CreateOrderControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            DAO dao = new DAO();
            String username = acc.getUsername();

            // 2. Lấy thông tin từ Session (đã được CheckoutControl tính toán trước đó)
            // Hoặc lấy trực tiếp từ DB để an toàn hơn
            List<CartItem> cartItems = dao.getCartItems(username);
            
            if (cartItems.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }

            // 3. Lấy thông tin từ Form Checkout.jsp (nếu có gửi lên)
            // (Hiện tại Checkout.jsp của bạn có thể đang dùng session để hiển thị, 
            // nhưng khi submit form cần gửi kèm các thông tin này hoặc tính lại)
            
            // Tạm thời tính lại tổng tiền để đảm bảo chính xác
            long subtotal = dao.getCartSubtotal(username);
            long shippingFee = (subtotal > 500000) ? 0 : 30000;
            long totalAmount = subtotal + shippingFee;
            
            String paymentMethod = request.getParameter("paymentMethod");
            // Lấy thông tin giao hàng từ form (nếu người dùng thay đổi)
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            
            // Cập nhật thông tin giao hàng tạm thời vào Account object để truyền cho DAO (hoặc truyền riêng)
            acc.setFullName(fullName);
            acc.setPhone(phone);
            acc.setAddress(address);

            // 4. Gọi DAO để tạo đơn hàng
            // Hàm createOrder này cần được thêm vào DAO.java (xem bên dưới)
            boolean orderCreated = dao.createOrder(acc, cartItems, totalAmount, shippingFee, paymentMethod);

            if (orderCreated) {
                // 5. Xóa giỏ hàng sau khi đặt thành công
                dao.clearCart(username);
                
                // 6. Chuyển hướng đến trang thông báo thành công hoặc lịch sử đơn hàng
                response.sendRedirect("account?tab=orders&checkout=success");
            } else {
                response.sendRedirect("checkout?error=true");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("checkout?error=true");
        }
    }
}