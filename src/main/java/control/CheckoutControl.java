package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.CartItem;
import com.mycompany.javaweb.entity.Order;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.mycompany.javaweb.dao.StockDAO;
import com.mycompany.javaweb.entity.Product;

@WebServlet(name = "CheckoutControl", urlPatterns = {"/checkout"})
public class CheckoutControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        
        if (acc == null) {
            response.sendRedirect("login?returnUrl=checkout");
            return;
        }
        long maKH = acc.getCustomerId();
        // --- Lấy giỏ hàng và tổng từ session ---
        List<CartItem> items = (List<CartItem>) session.getAttribute("cartItemsForCheckout");
        Long subtotalObj = (Long) session.getAttribute("subtotalForCheckout");
        Long shippingObj = (Long) session.getAttribute("shippingForCheckout");
        Long totalObj = (Long) session.getAttribute("totalForCheckout");

        if (items == null || items.isEmpty() || subtotalObj == null || shippingObj == null || totalObj == null) {
            response.sendRedirect("cart");
            return;
        }

        long subtotal = subtotalObj.longValue();
        long shipping = shippingObj.longValue();
        long total = totalObj.longValue();

        // --- Lấy thông tin người dùng nhập ---
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String paymentMethod = request.getParameter("paymentMethod");
        String shippingAddress = fullName + ", " + phone + ", " + address;

        // Kiểm tra thông tin người dùng nhập
        if (fullName == null || fullName.trim().isEmpty()
                || address == null || address.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin.");
            request.getRequestDispatcher("Checkout.jsp").forward(request, response);
            return;
        }

        StockDAO stockDAO = new StockDAO();
        String userEmail = acc.getEmail();
        String orderNumber = "MAU" + System.currentTimeMillis();

        // Kiểm tra phương thức thanh toán và tạo đơn hàng
        if ("VNPay".equals(paymentMethod)) {
            boolean validStock = true;
            for (CartItem item : items) {
                if (!stockDAO.hasEnoughStock(item.getProductId(), item.getQuantity())) {
                    validStock = false;
                    break;
                }
            }

            if (!validStock) {
                response.sendRedirect("cart?error=notenoughstock");
                return;
            }

            
            response.sendRedirect("vnpay");
            return;
        }

        if ("COD".equals(paymentMethod)) {
            // Kiểm tra tồn kho trước khi tạo đơn hàng
            boolean validStock = true;
            for (CartItem item : items) {
                if (!stockDAO.hasEnoughStock(item.getProductId(), item.getQuantity())) {
                    validStock = false;
                    break;
                }
            }

            if (!validStock) {
                response.sendRedirect("cart?error=notenoughstock");
                return;
            }

            // Tạo đơn hàng và giảm tồn kho
            Order order = new Order(orderNumber, userEmail, items, subtotal, shipping, total,
                    new Date(), "Chờ xác nhận", shippingAddress, phone, "COD");
            stockDAO.createOrder(order,maKH); // Gọi từ StockDAO để tạo đơn hàng và xử lý tồn kho

            // --- Giảm tồn kho sau khi thanh toán ---
            stockDAO.reduceStockAfterCheckout(items);  // Truyền giỏ hàng sang để giảm tồn kho
            
            // Xóa giỏ hàng
            stockDAO.clearCart(maKH);
            session.removeAttribute("cartItemsForCheckout");
            session.removeAttribute("subtotalForCheckout");
            session.removeAttribute("shippingForCheckout");
            session.removeAttribute("totalForCheckout");

            response.sendRedirect("account?checkout=success");
            return;
        }

        // Nếu không hợp lệ phương thức thanh toán
        response.sendRedirect("checkout?error=invalid_payment");
    }
}
