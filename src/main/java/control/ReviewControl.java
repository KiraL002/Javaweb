package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.ReviewDAO;
import com.mycompany.javaweb.entity.Account;
import entity.Review;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "ReviewControl", urlPatterns = {"/review"})
public class ReviewControl extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int productId = Integer.parseInt(request.getParameter("productId"));
        int star = Integer.parseInt(request.getParameter("star"));
        String comment = request.getParameter("comment");

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("acc");

        if (acc == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        ReviewDAO dao = new ReviewDAO();

        Review r = new Review();
        r.setProductId((long) productId);
        r.setUserId(acc.getCustomerId());
        r.setStar(star);
        r.setComment(comment);

        dao.addReview(r);

        response.sendRedirect("DetailControl?pid=" + productId);
    }
}
