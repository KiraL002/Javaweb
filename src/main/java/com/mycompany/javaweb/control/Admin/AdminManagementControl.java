package com.mycompany.javaweb.control.Admin;


import com.mycompany.javaweb.dao.AdminDAO;
import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin")
public class AdminManagementControl extends HttpServlet {
    private static final String ADMIN_PAGE = "AdminManagement.jsp";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tab =  request.getParameter("tab");
        AdminDAO dao = new AdminDAO();
        if(tab == null){
            tab = "users";
        }
        switch (tab) {
            case "users":
                List<Account> users = dao.getAllAccounts();
                request.setAttribute("users",users);
                break;
            case "orders":
                List<Order> orders = dao.getAllOrders();
                request.setAttribute("orders",orders);
                break;
            case "products":
                break;
            default:
                tab = "users";
                break;
        }
        request.setAttribute("activeTab", tab);
        request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
    }
}
