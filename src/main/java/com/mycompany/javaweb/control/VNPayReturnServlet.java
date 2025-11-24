package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.StockDAO;
import com.mycompany.javaweb.context.VNPAYUtils;
import com.mycompany.javaweb.entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "VNPayReturnServlet", urlPatterns = {"/vnpay_return"})
public class VNPayReturnServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(VNPayReturnServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Map<String, String[]> fields = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        StockDAO stockDAO = new StockDAO();
        Account acc = (Account) session.getAttribute("account");
        // Lấy orderId từ session thay vì từ VNPay
        String orderId = (String) session.getAttribute("currentOrderId");

        // Kiểm tra nếu không có orderId trong session
        if (orderId == null) {
            LOGGER.warning("Không tìm thấy orderId trong session.");
            response.sendRedirect("account?checkout=error");
            return;
        }

        // Lưu trữ các tham số trả về từ VNPay
        for (Map.Entry<String, String[]> entry : fields.entrySet()) {
            params.put(entry.getKey(), entry.getValue()[0]);
        }

        LOGGER.info("Các tham số nhận được từ VNPay: " + params);

        String vnp_SecureHash = params.get("vnp_SecureHash");
        params.remove("vnp_SecureHash");
        params.remove("vnp_SecureHashType");

        // Tạo rawData để kiểm tra chữ ký
        StringBuilder rawData = new StringBuilder();
        Iterator<String> keySet = new TreeSet<>(params.keySet()).iterator();
        while (keySet.hasNext()) {
            String key = keySet.next();
            String value = params.get(key);
            if (value != null && !value.isEmpty()) {
                rawData.append(key).append("=").append(URLDecoder.decode(value, "UTF-8"));
                if (keySet.hasNext()) {
                    rawData.append("&");
                }
            }
        }

        
        String responseCode = request.getParameter("vnp_ResponseCode");

        
        // Kiểm tra mã phản hồi từ VNPay (mã trạng thái thanh toán)
        try {
            if ("00".equals(responseCode)) {
                long maKH = acc.getCustomerId();
                // Cập nhật trạng thái đơn hàng thành "Đã thanh toán"
                stockDAO.updatePaymentStatus(orderId, "Đã thanh toán");
                stockDAO.clearCart(maKH);
                response.sendRedirect("account?checkout=success");
            } else {
                // Nếu thanh toán thất bại, cập nhật trạng thái là "Thanh toán thất bại"
                stockDAO.updatePaymentStatus(orderId, "Thanh toán thất bại");
                LOGGER.warning("Thanh toán VNPay thất bại cho order: " + orderId);
                response.sendRedirect("account?checkout=failed");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Lỗi xử lý VNPay return cho order: " + orderId, ex);
            response.sendRedirect("account?checkout=error");
        }
    }
}
