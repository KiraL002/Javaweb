package com.mycompany.javaweb.control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AccountControl", urlPatterns = {"/account"})
public class AccountControl extends HttpServlet {

    private static final String ACCOUNT_PAGE = "Account.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login"); 
            return;
        }
        
        String tab = request.getParameter("tab");
        if (tab == null) {
            tab = "orders"; // Mặc định
        }

        // Xử lý logic cho từng tab
        switch (tab) {
            case "orders":
                // (Logic lấy đơn hàng)
                break;
            case "favorites":
                // (Logic lấy yêu thích)
                break;
            case "profile":
                // (Không cần làm gì)
                break;
            // ĐÃ XÓA: case "password"
            default:
                tab = "orders";
                break;
        }

        request.setAttribute("activeTab", tab);
        request.getRequestDispatcher(ACCOUNT_PAGE).forward(request, response);
    }
}