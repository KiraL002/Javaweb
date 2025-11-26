package com.mycompany.javaweb.context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp này quản lý kết nối đến CSDL MySQL.
 * ĐÃ THÊM: hàm main() để test kết nối trực tiếp.
 */
public class DBContext {

    // --- Cấu hình CSDL ---
    // Sửa các thông số này cho khớp với CSDL của bạn (XAMPP, MySQL Workbench, ...)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/clothing_shop?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER_NAME = "root"; // Tên người dùng CSDL, 'root' là mặc định
    
    // HÃY SỬA LẠI MẬT KHẨU CỦA BẠN Ở ĐÂY
    private static final String PASSWORD = "Son28022005dz"; // <-- SỬA LẠI THÀNH MẬT KHẨU CỦA BẠN (ví dụ: "123456", "root", ...)

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1. Nạp Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Tạo kết nối
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("KẾT NỐI CSDL THẤT BẠI: " + ex.getMessage());
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn; // Sẽ trả về null nếu 'catch' bị lỗi
    }
    
    /**
     * HÀM MAIN ĐỂ TEST
     * Bạn có thể nhấp chuột phải vào tệp này và chọn 'Run File'
     */
    public static void main(String[] args) {
        System.out.println("--- Đang kiểm tra kết nối CSDL ---");
        
        // Nhắc nhở kiểm tra mật khẩu
        if (PASSWORD.equals("123456")) { // Giả sử mật khẩu cũ
             System.out.println("CẢNH BÁO: Bạn đang dùng mật khẩu '123456'. Hãy chắc chắn đây là mật khẩu MySQL (XAMPP) của bạn.");
        }
        if (PASSWORD.isEmpty()) {
             System.out.println("THÔNG TIN: Bạn đang dùng mật khẩu rỗng (mặc định của XAMPP).");
        }
        
        Connection conn = getConnection();
        
        if (conn != null) {
            System.out.println(">>> KẾT NỐI THÀNH CÔNG! <<<");
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">>> KẾT NỐI THẤT BẠI! <<<");
            System.out.println(">>> Vui lòng kiểm tra lại: ");
            System.out.println("1. Dịch vụ MySQL (XAMPP) đã chạy chưa?");
            System.out.println("2. CSDL 'clothing_shop' đã được tạo (bằng cách chạy tệp .sql) chưa?");
            System.out.println("3. USER_NAME và PASSWORD trong file này đã đúng chưa?");
        }
        System.out.println("--- Kiểm tra kết thúc ---");
    }
}