package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "EditControl", urlPatterns = {"/edit"})
public class EditControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 1. Lấy dữ liệu cũ
        String pid = request.getParameter("id");
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String stock = request.getParameter("stock");
        String sold = request.getParameter("sold");

        // 2. Lấy dữ liệu MỚI (Size, Màu, Chất liệu)
        String sizes = request.getParameter("sizes");
        String colors = request.getParameter("colors");
        String material = request.getParameter("material");

        DAO dao = new DAO();

        // 3. Gọi hàm updateProduct mới (11 tham số) trong DAO
        dao.updateProduct(pid, name, image, price, description, category, stock, sold, sizes, colors, material);

        // 4. SỬA LỖI: Chuyển hướng về trang Admin mới
        response.sendRedirect("admin?tab=products");
    }
}