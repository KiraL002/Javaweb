package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddControl", urlPatterns = {"/add"})
public class AddControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Lấy tiếng Việt có dấu
        request.setCharacterEncoding("UTF-8");

        // 2. Nhận dữ liệu từ form modal
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String stock = request.getParameter("stock");

        // 3. Gửi sang DAO để lưu
        DAO dao = new DAO();
        dao.insertProduct(name, image, price, title, description, category, stock);

        // 4. Quay về trang quản lý
        response.sendRedirect("manage-product");
    }
}