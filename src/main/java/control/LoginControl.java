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
            HttpSession session = request.getSession();
            // ĐẢM BẢO LƯU TÊN LÀ "account" (Phần này đã đúng)
            session.setAttribute("account", account); 
            session.setMaxInactiveInterval(1800); 
            
            // SỬA: Kiểm tra xem có returnUrl không
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
        
        // === SỬA LOGIC TẠI ĐÂY ===
        // Kiểm tra xem người dùng đã đăng nhập chưa
        HttpSession session = request.getSession(false); // false = không tạo session mới
        if (session != null && session.getAttribute("account") != null) {
            // Nếu đã đăng nhập, chuyển về trang chủ
            response.sendRedirect("home");
            return;
        }
        
        // Nếu chưa đăng nhập, mới hiển thị trang Login
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}