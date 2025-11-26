package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Product;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ProductManagerControl", urlPatterns = {"/manage-product"})
public class ProductManagerControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 1. Kiểm tra quyền Admin (Bảo mật)
        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("account"); // Biến session trong LoginControl của bạn tên là gì? Thường là "account" hoặc "acc"

        // Nếu chưa đăng nhập hoặc không phải ADMIN thì đá về trang chủ
        if (a == null || !"ADMIN".equalsIgnoreCase(a.getRole())) {
            response.sendRedirect("login"); // Hoặc trang báo lỗi
            return;
        }

        // 2. Gọi DAO lấy danh sách
        DAO dao = new DAO();
        // Dùng hàm getAllProducts có sẵn trong DAO của bạn
        List<Product> list = dao.getAllProducts();

        // 3. Đẩy dữ liệu ra view
        request.setAttribute("listP", list);

        // 4. Chuyển hướng
        request.getRequestDispatcher("ManagerProduct.jsp").forward(request, response);
    }
}
