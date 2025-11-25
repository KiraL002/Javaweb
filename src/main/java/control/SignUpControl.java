package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignupControl", urlPatterns = {"/signup"})
public class SignUpControl extends HttpServlet {

    // Giả sử file SignUp.jsp của bạn ở ngoài WEB-INF (theo lần sửa trước)
    private static final String SIGNUP_PAGE = "SignUp.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // ĐÃ SỬA: Lấy đúng tên biến từ form JSP của bạn
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");

        // 1. Kiểm tra Null (Nguyên nhân gây lỗi 500)
        if (password == null || repassword == null) {
            request.setAttribute("error", "Lỗi: Mật khẩu không được để trống.");
            request.getRequestDispatcher(SIGNUP_PAGE).forward(request, response);
            return;
        }

        // 2. Kiểm tra mật khẩu
        if (!password.equals(repassword)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher(SIGNUP_PAGE).forward(request, response);
            return; 
        }
        
        DAO dao = new DAO();
        
        // 3. Kiểm tra tài khoản tồn tại
        boolean userExists = dao.checkAccountExists(username); 
        
        if (userExists) {
            // TÀI KHOẢN ĐÃ TỒN TẠI
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher(SIGNUP_PAGE).forward(request, response);
        } else {
            // ĐĂNG KÝ THÀNH CÔNG
            // Gọi hàm signUp (BCrypt) của DAO
            dao.signUp(username, fullName, password, phone, null); 
            
            // Chuyển hướng về trang đăng nhập
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(SIGNUP_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}