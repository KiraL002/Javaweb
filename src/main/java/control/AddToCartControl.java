package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import com.mycompany.javaweb.entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AddToCartControl", urlPatterns = {"/add-to-cart"})
public class AddToCartControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        Account acc = (Account) session.getAttribute("account");

        String productId_raw = request.getParameter("productId");

        if (acc == null) {
            response.sendRedirect("login?returnUrl=detail?id=" + productId_raw);
            return;
        }

        String size = request.getParameter("size");
        String color = request.getParameter("color");
        int quantity = 1;
        int productId = 0;

        try {
            productId = Integer.parseInt(productId_raw);
            // Lấy số lượng từ form, nếu không có (trang shop) thì mặc định là 1
            String quantity_raw = request.getParameter("quantity");
            if (quantity_raw != null && !quantity_raw.isEmpty()) {
                quantity = Integer.parseInt(quantity_raw);
            }
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số lượng hoặc ID: " + e.getMessage());
        }

        DAO dao = new DAO();

        // Lấy sản phẩm từ DB
        var product = dao.getProductById(productId);

        // Kiểm tra xem sản phẩm còn hàng hay không
        if (product.getStock() <= 0) {
            // **Quan trọng:** Thay đổi chuyển hướng để hiển thị thông báo thành công/lỗi
            response.sendRedirect("detail?id=" + productId + "&error=outofstock");
            return;
        }

        // Kiểm tra nếu số lượng request lớn hơn số lượng tồn kho
        if (quantity > product.getStock()) {
            // **Quan trọng:** Thay đổi chuyển hướng để hiển thị thông báo thành công/lỗi
            response.sendRedirect("detail?id=" + productId + "&error=notenoughstock");
            return;
        }

        // Thêm vào giỏ hàng hoặc cập nhật số lượng
        // Logic này giả định được xử lý trong DAO
        dao.addToCart(productId, quantity, size, color, acc.getUsername());

        // Chuyển hướng về trang chi tiết sản phẩm và thêm param cartSuccess để hiển thị thông báo
        response.sendRedirect("detail?id=" + productId + "&cartSuccess=true");
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
