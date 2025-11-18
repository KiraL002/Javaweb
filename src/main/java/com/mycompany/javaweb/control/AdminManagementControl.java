package com.mycompany.javaweb.control;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/admin")
public class AdminManagementControl extends HttpServlet {
    private static final String ADMIN_PAGE = "AdminManagement.jsp";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type =  request.getParameter("admin");
        request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
    }
}
