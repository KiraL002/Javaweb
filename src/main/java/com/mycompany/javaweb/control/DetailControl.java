package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Product;
import java.io.IOException;
import java.util.List; // Thêm import
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ĐÃ SỬA:
 * - Thêm logic để lấy sản phẩm liên quan (getRelatedProducts)
 */
@WebServlet(name = "DetailControl", urlPatterns = {"/detail"})
public class DetailControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. Lấy 'id' từ URL
        String id_raw = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(id_raw);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse ID: " + e.getMessage());
        }

        // 2. Gọi DAO
        DAO dao = new DAO();
        Product product = dao.getProductById(id);
        
        // 3. (MỚI) Lấy sản phẩm liên quan
        List<Product> relatedList = dao.getRelatedProducts(product);

        // 4. Set dữ liệu lên request
        request.setAttribute("product", product);
        request.setAttribute("relatedProducts", relatedList); // (MỚI)

        // 5. Chuyển tiếp đến JSP
        request.getRequestDispatcher("Detail.jsp").forward(request, response);
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

