package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.CartItem;
import com.mycompany.javaweb.entity.Order;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet mới: Xử lý logic cho trang Thanh toán
 */
@WebServlet(name = "CheckoutControl", urlPatterns = {"/checkout"})
public class CheckoutControl extends HttpServlet {

    /**
     * doGet: Hiển thị trang thanh toán
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("acc");

        // 1. Yêu cầu đăng nhập
        if (acc == null) {
            response.sendRedirect("login?returnUrl=cart");
            return;
        }

        DAO dao = new DAO();
        List<CartItem> cartItems = dao.getCartItems(acc.getEmail());
        
        // 2. Nếu giỏ hàng trống, chuyển về /cart
        if (cartItems.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        // 3. Tính toán tổng tiền
        long subtotal = dao.getCartSubtotal(acc.getEmail());
        long shipping = (subtotal > 500000) ? 0 : 30000;
        long total = subtotal + shipping;

        // 4. Gửi dữ liệu sang Checkout.jsp
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total", total);
        
        request.getRequestDispatcher("Checkout.jsp").forward(request, response);
    }

    /**
     * doPost: Xử lý khi người dùng nhấn "Đặt hàng"
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("acc");

        // 1. Yêu cầu đăng nhập
        if (acc == null) {
            response.sendRedirect("login");
            return;
        }
        
        // 2. Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String paymentMethod = request.getParameter("paymentMethod");
        
        // Tạo địa chỉ đầy đủ (Ví dụ)
        String shippingAddress = fullName + ", " + phone + ", " + address;

        // 3. Lấy thông tin giỏ hàng từ DAO
        DAO dao = new DAO();
        String userEmail = acc.getEmail();
        List<CartItem> items = dao.getCartItems(userEmail);
        
        // Phải tạo một bản sao của danh sách, vì DAO sẽ xóa bản gốc
        List<CartItem> orderItems = new ArrayList<>(items); 
        
        long subtotal = dao.getCartSubtotal(userEmail);
        long shipping = (subtotal > 500000) ? 0 : 30000;
        long total = subtotal + shipping;

        // 4. Tạo đối tượng Order
        String orderNumber = "#MAU" + new Date().getTime(); // Tạo mã đơn hàng duy nhất
        String status = "Chờ xác nhận";
        
        Order order = new Order(orderNumber, userEmail, orderItems, 
                              subtotal, shipping, total, new Date(), 
                              status, shippingAddress, phone, paymentMethod);

        // 5. Lưu đơn hàng vào DAO
        dao.createOrder(order);
        
        // 6. Xóa giỏ hàng
        dao.clearCart(userEmail);

        // 7. Chuyển đến trang Account, kèm thông báo thành công
        response.sendRedirect("account?checkout=success");
    }
}
