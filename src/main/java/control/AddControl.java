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

@WebServlet(name = "AddControl", urlPatterns = {"/add"})
public class AddControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // Để nhận tiếng Việt

        // 1. Kiểm tra quyền Admin
        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("account");
        if(a == null || a.getRole().equals("USER")) {
            response.sendRedirect("login");
            return;
        }

        // 2. Lấy dữ liệu từ Form (AdminManagement.jsp)
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        
        // Các trường mới
        String sizes = request.getParameter("sizes");
        String colors = request.getParameter("colors");
        String material = request.getParameter("material");

        // 3. Gọi DAO để lưu vào Database
        DAO dao = new DAO();
        // Hàm này sẽ được cập nhật ở bước dưới
        dao.insertProduct(name, image, price, description, category, stock, sizes, colors, material);

        // 4. QUAN TRỌNG: Chuyển hướng về trang Admin mới (Tab Sản phẩm)
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