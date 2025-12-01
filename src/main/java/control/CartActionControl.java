package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
// ... (các import giữ nguyên) ...

@WebServlet(name = "CartActionControl", urlPatterns = {"/cart-action"})
public class CartActionControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // === THÊM DEBUG TẠI ĐÂY ===
        System.out.println("--- CART ACTION DEBUG (PHIÊN BẢN MỚI) ---");
        // ============================

        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account"); 

        if (acc == null) {
            response.sendRedirect("login"); 
            return;
        }
        
        String action = request.getParameter("action");
        long cartItemId = 0; 
        try {
            cartItemId = Long.parseLong(request.getParameter("cartItemId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("cart"); 
            return;
        }

        DAO dao = new DAO();
        String username = acc.getUsername(); 

        // === THÊM DEBUG TẠI ĐÂY ===
        System.out.println("Username: " + username);
        System.out.println("Action: " + action);
        System.out.println("Cart Item ID: " + cartItemId);
        // ============================

        if ("update".equals(action)) {
            try {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                
                // === THÊM DEBUG TẠI ĐÂY ===
                System.out.println("New Quantity: " + quantity);
                // ============================
                
                if (quantity > 0) { 
                    dao.updateCartItemQuantity(cartItemId, quantity, username);
                } else {
                    dao.removeCartItem(cartItemId, username);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if ("remove".equals(action)) {
            dao.removeCartItem(cartItemId, username);
        }

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