package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.OrderDAO;
import com.mycompany.javaweb.entity.Order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class OrderAdminController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    // Hiển thị danh sách đơn hàng
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrders();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/admin/order-management.jsp").forward(req, resp);
    }

    // Cập nhật đơn hàng
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        String phone = req.getParameter("phone");
        String status = req.getParameter("status");

        orderDAO.updateOrder(orderId, phone, status);

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }

    // Xóa đơn hàng (AJAX)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderId = req.getParameter("orderId");
        boolean deleted = orderDAO.deleteOrder(orderId);
        resp.setStatus(deleted ? 200 : 500);
    }
}
