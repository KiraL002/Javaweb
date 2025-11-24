package com.mycompany.javaweb.control.AdminControl;

import com.mycompany.javaweb.dao.AdminDAO;
import com.mycompany.javaweb.entity.Order;
import entity.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import utils.StringUtils;

@WebServlet(urlPatterns = "/admin/order/*")
public class OrderAdminController extends HttpServlet {
    private AdminDAO dao = new AdminDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException, ServletException{
        //        Xử lí orderDetail
        String path = request.getPathInfo(); // id: "/123"
        if(path !=null && path.length() > 1){
            String Id = path.substring(1);
            Order order = dao.getOrderById(Id);
            List<OrderItem> orderItems = dao.getOrderItemsByOrderId(Id);
            System.out.println(Id);
            request.setAttribute("order",order);
            request.setAttribute("items", orderItems);
        }
        request.getRequestDispatcher("/Admin/OrderDetailManagment.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
        request.setCharacterEncoding("UTF-8");
    
//        request
        String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        String paymentMethod = request.getParameter("paymentMethod");
        if(!StringUtils.isEmpty(status) || !StringUtils.isEmpty(paymentMethod)){
             dao.updateOrder(orderId, status, paymentMethod);
        }
        
        response.sendRedirect("/admin?tab=orders");
    }
}
