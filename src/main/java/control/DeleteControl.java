package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteControl", urlPatterns = {"/delete"})
public class DeleteControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. Lấy ID sản phẩm cần xóa từ URL (ví dụ: delete?pid=1)
        String pid = request.getParameter("pid");
        
        // 2. Gọi DAO để xóa sản phẩm
        DAO dao = new DAO();
        dao.deleteProduct(pid);
        
        // 3. QUAN TRỌNG: Chuyển hướng về trang Admin mới (Tab Sản phẩm)
        // Thay vì về "manager" hay "ManagerProduct.jsp" (đã xóa)
        response.sendRedirect("admin?tab=products");
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