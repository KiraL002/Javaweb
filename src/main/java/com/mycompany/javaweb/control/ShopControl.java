package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Brand;
import com.mycompany.javaweb.entity.Category;
import com.mycompany.javaweb.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShopControl", urlPatterns = {"/shop"})
public class ShopControl extends HttpServlet {

    private static final int PAGE_SIZE = 20;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        DAO dao = new DAO();

        // 1. Lấy dữ liệu cho bộ lọc
        List<Category> listC = dao.getAllCategories();
        List<Brand> listB = dao.getAllBrands();

        // 2. Lấy tham số filter và trang
        String keyword = request.getParameter("keyword");
        String categoryId_raw = request.getParameter("categoryId");
        String brandId_raw = request.getParameter("brandId");
        String page_raw = request.getParameter("page");

        // Chuyển đổi tham số
        long categoryId = 0;
        long brandId = 0;
        int pageIndex = 1; 

        try {
            if (categoryId_raw != null && !categoryId_raw.isEmpty()) categoryId = Long.parseLong(categoryId_raw);
            if (brandId_raw != null && !brandId_raw.isEmpty()) brandId = Long.parseLong(brandId_raw);
            if (page_raw != null && !page_raw.isEmpty()) pageIndex = Integer.parseInt(page_raw);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            pageIndex = 1; 
        }

        // 3. Xử lý Phân trang
        // 3.1. Đếm tổng số sản phẩm (theo filter)
        int totalProducts = dao.countProducts(keyword, categoryId, brandId);
        
        // 3.2. Tính tổng số trang
        int endPage = totalProducts / PAGE_SIZE;
        if (totalProducts % PAGE_SIZE != 0) {
            endPage++; 
        }

        // 3.3. Lấy danh sách sản phẩm cho trang hiện tại
        List<Product> listP = dao.getPaginatedProducts(keyword, categoryId, brandId, pageIndex, PAGE_SIZE);

        // 4. Gửi dữ liệu sang Shop.jsp
        
        // === SỬA LỖI TẠI ĐÂY: Dùng lại tên biến CŨ ===
        request.setAttribute("productList", listP);     // Dùng 'productList' thay vì 'products'
        request.setAttribute("categoryList", listC); // Dùng 'categoryList' thay vì 'categories'
        request.setAttribute("brandList", listB);     // Dùng 'brandList' thay vì 'brands'
        
        request.setAttribute("selectedKeyword", (keyword != null ? keyword : "")); // Giữ lại keyword
        request.setAttribute("selectedCategoryId", categoryId); // Giữ lại categoryId
        request.setAttribute("selectedBrandId", brandId);     // Giữ lại brandId
        // ===============================================

        // Các biến mới cho phân trang (vẫn giữ nguyên)
        request.setAttribute("totalProducts", totalProducts); 
        request.setAttribute("endPage", endPage);             
        request.setAttribute("currentPage", pageIndex);       
        
        // Giữ lại tham số URL cho các nút phân trang (để tránh null)
        request.setAttribute("keyword", (keyword != null ? keyword : ""));
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("brandId", brandId);

        request.getRequestDispatcher("Shop.jsp").forward(request, response);
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