package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account; // Thêm import Account
import com.mycompany.javaweb.entity.Product;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Thêm import HttpSession
import com.mycompany.javaweb.dao.ReviewDAO;
import entity.Review;

@WebServlet(name = "DetailControl", urlPatterns = {"/detail"})
public class DetailControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // ... (Phần lấy ID và Product giữ nguyên) ...
        String id_raw = request.getParameter("id");
        int id = 0;
        try { id = Integer.parseInt(id_raw); } catch (Exception e) {}

        DAO dao = new DAO();
        Product product = dao.getProductById(id);
        
        // ... (Phần lấy Review và Related Products giữ nguyên) ...
        ReviewDAO reviewDAO = new ReviewDAO();
        request.setAttribute("reviewList", reviewDAO.getReviewsByProductId(id));
        request.setAttribute("relatedProducts", dao.getRelatedProducts(product));

        boolean canReview = false;
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        
        if (acc != null) {
            // CÁCH CUỐI: Dùng Username để kiểm tra
            String username = acc.getUsername();
            canReview = dao.checkUserBoughtProduct(username, id);
            
            // Debug ra console của NetBeans để bạn xem kết quả ngay
            System.out.println("DetailControl: User " + username + " can review product " + id + "? -> " + canReview);
        }
        
        request.setAttribute("canReview", canReview);
        // ==================================================

        request.setAttribute("product", product);
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