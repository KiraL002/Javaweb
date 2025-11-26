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

@WebServlet(name = "UpdateProfileControl", urlPatterns = {"/update-profile"})
public class UpdateProfileControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        DAO dao = new DAO();
        String action = request.getParameter("action");

        if ("updateInfo".equals(action)) {
            // --- XỬ LÝ CẬP NHẬT THÔNG TIN ---
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            dao.updateAccountInfo(account.getUserId(), fullName, phone, address);
            
            // Cập nhật lại đối tượng Account trong session
            account.setFullName(fullName);
            account.setPhone(phone);
            account.setAddress(address);
            session.setAttribute("account", account); 

            // Chuyển hướng về trang tài khoản với thông báo thành công
            response.sendRedirect("account?tab=profile&update=success");
            
        } else {
            // ĐÃ XÓA: Khối "else if (changePass)"
            
            // Nếu action không phải là 'updateInfo', mặc định quay về trang profile
            response.sendRedirect("account?tab=profile");
        }
    }
}