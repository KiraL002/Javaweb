package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddToCartControl", urlPatterns = {"/add-to-cart"})
public class AddToCartControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        Account acc = (Account) session.getAttribute("account"); 
        
        String productId_raw = request.getParameter("productId");
        
        if (acc == null) {
            response.sendRedirect("login?returnUrl=detail?id=" + productId_raw);
            return;
        }

        String size = request.getParameter("size");
        String color = request.getParameter("color");
        int quantity = 1;
        int productId = 0;
        
        try {
            productId = Integer.parseInt(productId_raw);
            // Lấy số lượng từ form, nếu không có (trang shop) thì mặc định là 1
            String quantity_raw = request.getParameter("quantity");
            if (quantity_raw != null && !quantity_raw.isEmpty()) {
                quantity = Integer.parseInt(quantity_raw);
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số lượng hoặc ID: " + e.getMessage());
        }

        DAO dao = new DAO();
        
        // === SỬA LỖI LOGIC TẠI ĐÂY ===
        // Phải dùng getUsername() để khớp với logic của DAO
        dao.addToCart(productId, quantity, size, color, acc.getUsername());
        
        response.sendRedirect("cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}