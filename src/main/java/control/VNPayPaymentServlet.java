package com.mycompany.javaweb.control;

import com.mycompany.javaweb.context.VNPAYUtils;
import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.dao.StockDAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.CartItem;
import com.mycompany.javaweb.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@WebServlet(name = "VNPayPaymentServlet", urlPatterns = {"/vnpay"})
public class VNPayPaymentServlet extends HttpServlet {

    // Khởi tạo logger
    private static final Logger LOGGER = Logger.getLogger(VNPayPaymentServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Account acc = (Account) session.getAttribute("account");
        long maKH = acc.getCustomerId();
        
        if (acc == null) {
            resp.sendRedirect("login?returnUrl=checkout");
            return;
        }

        StockDAO stockDAO = new StockDAO();
        List<CartItem> items = (List<CartItem>) session.getAttribute("cartItemsForCheckout");

        if (items == null || items.isEmpty()) {
            resp.sendRedirect("checkout?error=session_expired");
            return;
        }

        // --- Tính toán subtotal, shipping, total ---
        long subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        long shipping = (subtotal > 500_000) ? 0 : 30_000;
        long total = subtotal + shipping;

        // --- Tạo order mới ---
        String orderId = "MAU" + System.currentTimeMillis();
        Order order = new Order(orderId, acc.getEmail(), items, subtotal, shipping, total,
                new Date(), "Chờ thanh toán",
                acc.getFullName() + ", " + acc.getPhone() + ", " + acc.getAddress(),
                acc.getPhone(), "VNPay");
        stockDAO.createOrder(order,maKH);
        session.setAttribute("currentOrderId", orderId);
        // --- Lưu session ---
        session.setAttribute("currentOrderId", orderId);
        session.setAttribute("totalForCheckout", total);

        try {
            Map<String, String> params = new HashMap<>();
            params.put("vnp_Version", "2.1.0");
            params.put("vnp_Command", "pay");
            params.put("vnp_TmnCode", VNPAYUtils.vnp_TmnCode);
            params.put("vnp_Amount", String.valueOf(total * 100)); // VNPay yêu cầu số tiền nhân với 100
            params.put("vnp_CurrCode", "VND");
            params.put("vnp_TxnRef", orderId);
            params.put("vnp_OrderInfo", "Thanh toán đơn hàng " + orderId);
            params.put("vnp_OrderType", "other");
            params.put("vnp_Locale", "vn");
            params.put("vnp_ReturnUrl", VNPAYUtils.vnp_ReturnUrl);
            params.put("vnp_IpAddr", req.getRemoteAddr());

            // --- Thời gian hiện tại (CreateDate) và hết hạn (ExpireDate) ---
            String createDate = VNPAYUtils.getCurrentDateTime(); // yyyyMMddHHmmss
            params.put("vnp_CreateDate", createDate);

            // Expire 15 phút sau
            params.put("vnp_ExpireDate", VNPAYUtils.getExpireDateTime(15));

            // --- Tạo URL và redirect ---
            String paymentUrl = VNPAYUtils.createPaymentUrl(params);

            // Log các tham số thanh toán và URL thanh toán
            LOGGER.info("Tạo các tham số thanh toán VNPay: " + params);
            LOGGER.info("Tạo URL thanh toán VNPay: " + paymentUrl);

            resp.sendRedirect(paymentUrl);

        } catch (Exception e) {
            LOGGER.severe("!!! LỖI TẠO URL VNPAY: " + e.getMessage());
            e.printStackTrace();
            resp.sendRedirect("checkout?error=vnpay_fail&detail=" + e.getClass().getSimpleName());
        }
    }
}
