package com.mycompany.javaweb.control;

import com.mycompany.javaweb.dao.DAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Khi bấm nút Thùng rác, nó sẽ gọi vào đường dẫn /delete
@WebServlet(name = "DeleteControl", urlPatterns = {"/delete"})
public class DeleteControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy id sản phẩm cần xóa từ đường dẫn (VD: delete?pid=1)
        String pid = request.getParameter("pid");

        // 2. Gọi DAO để xóa
        DAO dao = new DAO();
        dao.deleteProduct(pid);

        // 3. Xóa xong thì quay lại trang quản lý
        response.sendRedirect("manage-product");
    }
}