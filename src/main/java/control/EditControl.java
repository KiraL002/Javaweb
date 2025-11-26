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

        String pid = request.getParameter("id");
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String stock = request.getParameter("stock");

        // 1. THÊM DÒNG NÀY: Lấy dữ liệu "Đã bán" từ form
        String sold = request.getParameter("sold");

        DAO dao = new DAO();

        // 2. SỬA DÒNG NÀY: Truyền đủ 8 tham số sang DAO
        dao.updateProduct(pid, name, image, price, description, category, stock, sold);

        response.sendRedirect("manage-product");
    }
}