package com.mycompany.javaweb.control.Admin;

import com.mycompany.javaweb.dao.AdminDAO;
import com.mycompany.javaweb.dao.DAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/users")
public class UserAdminController  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Long userId = Long.valueOf(request.getParameter("userId"));

//        Lấy dữ liệu được chỉnh sửa
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

//        Cập nhật
        AdminDAO dao = new AdminDAO();
        dao.updateAccountInfo(userId, username, phone, role, status);
        response.sendRedirect("/admin?tab=users");
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Long userId = Long.valueOf(request.getParameter("userId"));

        AdminDAO dao = new AdminDAO();
        boolean success = dao.deleteAccount(userId);

        if(success){
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
