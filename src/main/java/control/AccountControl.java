package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.OrderDAO;
import com.mycompany.javaweb.dao.StockDAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "AccountControl", urlPatterns = {"/account"})
public class AccountControl extends HttpServlet {

    private OrderDAO dao = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        // Lấy tài khoản người dùng từ session
        Account acc = (Account) session.getAttribute("account");

        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = "orders"; // Mặc định
        }

        // Xử lý logic cho từng tab
        switch (tab) {
            case "orders":
                // Lấy danh sách đơn hàng của người dùng
                List<Order> orders = dao.getOrdersByCustomer(acc.getCustomerId());
                request.setAttribute("userOrders", orders);
                break;
            case "favorites":
                // (Logic lấy yêu thích)
                break;
            case "profile":
                // (Không cần làm gì)
                break;
            default:
                tab = "orders";
                break;
        }

        // Set thuộc tính tab hiện tại
        request.setAttribute("activeTab", tab);

        // Forward đến trang Account.jsp
        request.getRequestDispatcher("Account.jsp").forward(request, response);
    }
    
    // Xóa đơn hàng (AJAX)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        resp.setContentType("application/json;charset=UTF-8");

        if (orderId == null || orderId.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"message\":\"orderId is required\"}");
            return;
        }
        
        boolean deleted = dao.deleteOrder(orderId);
        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\":\"Order deleted successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\":\"Failed to delete order\"}");
        }

    }
}
