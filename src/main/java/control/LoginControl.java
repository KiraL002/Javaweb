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

@WebServlet(name = "LoginControl", urlPatterns = {"/login"})
public class LoginControl extends HttpServlet {

    private static final String LOGIN_PAGE = "Login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        DAO dao = new DAO();
        Account account = dao.getAccount(username, password); 
        
        if (account == null) {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        } else {
            // === MỚI: KIỂM TRA TRẠNG THÁI TÀI KHOẢN ===
            // Nếu trạng thái là SUSPENDED thì chặn lại
            if ("SUSPENDED".equalsIgnoreCase(account.getStatus())) {
                request.setAttribute("error", "Tài khoản của bạn đã bị khóa! Vui lòng liên hệ Admin.");
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
                return; // Dừng xử lý, không cho tạo session
            }
            // ============================================

            HttpSession session = request.getSession();
            session.setAttribute("account", account); 
            session.setMaxInactiveInterval(1800); 
            
            // Kiểm tra xem có returnUrl không (để quay lại trang trước đó)
            String returnUrl = request.getParameter("returnUrl");
            if (returnUrl != null && !returnUrl.isEmpty()) {
                response.sendRedirect(returnUrl);
            } else {
                response.sendRedirect("home");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra xem người dùng đã đăng nhập chưa
        HttpSession session = request.getSession(false); 
        if (session != null && session.getAttribute("account") != null) {
            response.sendRedirect("home");
            return;
        }
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}