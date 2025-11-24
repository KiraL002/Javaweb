package com.mycompany.javaweb.control.AdminControl;

import com.mycompany.javaweb.dao.AdminDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import utils.StringUtils;

@WebServlet(urlPatterns = "/admin/orders")
public class OrderAdminController extends HttpServlet {
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
        request.setCharacterEncoding("UTF-8");
        AdminDAO dao = new AdminDAO();
        
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
