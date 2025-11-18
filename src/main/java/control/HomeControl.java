package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Category;
import com.mycompany.javaweb.entity.Product;
import java.io.IOException;
import java.util.List;
// ĐÃ THAY ĐỔI: Sử dụng 'jakarta' thay vì 'javax'
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet này xử lý logic cho Trang chủ
 * Nó dùng gói jakarta.* (Jakarta EE 9+ / Tomcat 10+)
 */
@WebServlet(name = "HomeControl", urlPatterns = {"/home"})
public class HomeControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        DAO dao = new DAO();
        
        // 1. Lấy dữ liệu từ DAO
        List<Category> listCategories = dao.getAllCategories();
        List<Product> listNewProducts = dao.getNewProducts();
        List<Product> listBestSellerProducts = dao.getBestSellerProducts();

        // 2. Đặt (set) dữ liệu lên request để đẩy sang JSP
        request.setAttribute("categories", listCategories);
        request.setAttribute("newProducts", listNewProducts);
        request.setAttribute("bestSellers", listBestSellerProducts);

        // 3. Chuyển tiếp (forward) đến file JSP (View)
        request.getRequestDispatcher("Home.jsp").forward(request, response);
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
