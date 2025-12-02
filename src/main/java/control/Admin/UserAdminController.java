package com.mycompany.javaweb.control.Admin;

import com.mycompany.javaweb.dao.AdminDAO;
import com.mycompany.javaweb.entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin/users")
public class UserAdminController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            Long userId = Long.valueOf(request.getParameter("userId"));

            // Lấy dữ liệu được chỉnh sửa
            String username = request.getParameter("username");
            String phone = request.getParameter("phone");
            String role = request.getParameter("role");
            String status = request.getParameter("status");

            //        Kiểm tra có đang chỉnh quyền của chính mình ko
            HttpSession session = request.getSession(false);
            Account acc = (Account) session.getAttribute("account");
            // ko phải admin
            if (!"ADMIN".equals(acc.getRole())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Bạn không có quyền thay đổi!");
                return;
            }
    
            // không được đổi role của chính mình
            if (acc.getUserId() == userId) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Bạn không thể chỉnh sửa chính mình!");
                return;
            }
            
            // Cập nhật
            AdminDAO dao = new AdminDAO();
            dao.updateAccountInfo(userId, username, phone, role, status);
            
            // SỬA: Redirect dùng đường dẫn tương đối hoặc lấy ContextPath
            response.sendRedirect(request.getContextPath() + "/admin?tab=users");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Long userId = Long.valueOf(request.getParameter("userId"));

            AdminDAO dao = new AdminDAO();
            boolean success = dao.deleteAccount(userId);

            if(success){
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
