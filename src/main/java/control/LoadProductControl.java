package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Product;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "LoadProductControl", urlPatterns = {"/loadProduct"})
public class LoadProductControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("pid");

        DAO dao = new DAO();
        Product p = dao.getProductByID(id);

        request.setAttribute("detail", p);

        // Chuyển sang trang Edit.jsp
        request.getRequestDispatcher("Edit.jsp").forward(request, response);
    }
}