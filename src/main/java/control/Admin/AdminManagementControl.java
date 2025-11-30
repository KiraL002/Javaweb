package com.mycompany.javaweb.control.Admin;

import com.mycompany.javaweb.dao.AdminDAO;
import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.dao.OrderDAO; // MỚI
import com.mycompany.javaweb.entity.Account;
import com.mycompany.javaweb.entity.Order;
import com.mycompany.javaweb.entity.Product;
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
        String tab = request.getParameter("tab");
        AdminDAO adminDao = new AdminDAO();
        OrderDAO orderDao = new OrderDAO(); // Khởi tạo OrderDAO
        
        if(tab == null){ tab = "users"; }
        
        switch (tab) {
            case "users":
                List<Account> users = adminDao.getAllAccounts();
                request.setAttribute("users", users);
                break;
                
            case "orders":
                // === SỬA TẠI ĐÂY: Dùng OrderDAO để lấy danh sách đơn ===
                List<Order> orders = orderDao.getAllOrders();
                request.setAttribute("orders", orders);
                break;
                
            case "products":
                DAO productDao = new DAO();
                List<Product> listP = productDao.getAllProducts();
                request.setAttribute("listP", listP);
                break;
                
            default:
                tab = "users";
                break;
        }
        
        request.setAttribute("activeTab", tab);
        request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
    }
}