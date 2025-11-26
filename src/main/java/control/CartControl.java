package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.CartItem;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CartControl", urlPatterns = {"/cart"})
public class CartControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        
        Account acc = (Account) session.getAttribute("account"); 

        if (acc == null) {
            response.sendRedirect("login?returnUrl=cart");
            return; 
        }
        
        // BƯỚC 2: Nếu đã đăng nhập, xử lý giỏ hàng
        DAO dao = new DAO();
        
        // === SỬA LỖI LOGIC TẠI ĐÂY ===
        // Phải dùng getUsername() để khớp với logic của DAO
        List<CartItem> cartItems = dao.getCartItems(acc.getUsername());
        long subtotal = dao.getCartSubtotal(acc.getUsername());
        
        long shipping = (subtotal > 500000 || cartItems.isEmpty()) ? 0 : 30000;
        long total = subtotal + shipping;

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total", total);
        
        request.getRequestDispatcher("Cart.jsp").forward(request, response);
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