package com.mycompany.javaweb.dao;

import com.mycompany.javaweb.context.DBContext;
import entity.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class ReviewDAO {

    public List<Review> getReviewsByProductId(int productId) {
        List<Review> list = new ArrayList<>();

        String sql = "SELECT * FROM DanhGia WHERE maSP = ? ORDER BY ngayTao DESC";

        try (Connection conn = new DBContext().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Review r = new Review();
                r.setId(rs.getLong("maDG"));
                r.setProductId(rs.getLong("maSP"));
                r.setUserId(rs.getLong("maKH"));
                r.setStar(rs.getInt("soSao"));
                r.setComment(rs.getString("noiDung"));
                r.setCreatedDate(rs.getTimestamp("ngayTao"));
                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public void addReview(Review r) {
    String insertSql = "INSERT INTO DanhGia (maSP, maKH, soSao, noiDung) VALUES (?, ?, ?, ?)";
    // Câu cập nhật tính trung bình và số lượng dựa trên bảng DanhGia
    String updateProductSql =
        "UPDATE SanPham SET " +
        " ratingTrungBinh = (SELECT IFNULL(AVG(soSao),0) FROM DanhGia WHERE maSP = ?), " +
        " soLuongDanhGia = (SELECT COUNT(*) FROM DanhGia WHERE maSP = ?) " +
        "WHERE maSP = ?";

    try (Connection conn = new DBContext().getConnection()) {
        // Nếu muốn atomic: tắt auto-commit
        conn.setAutoCommit(false);
        try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getProductId());
            ps.setLong(2, r.getUserId());
            ps.setInt(3, r.getStar());
            ps.setString(4, r.getComment());
            ps.executeUpdate();
            // try (ResultSet gk = ps.getGeneratedKeys()) { if (gk.next()) r.setId(gk.getLong(1)); }

            // Cập nhật lại thông tin rating trên SanPham
            try (PreparedStatement ps2 = conn.prepareStatement(updateProductSql)) {
                ps2.setLong(1, r.getProductId());
                ps2.setLong(2, r.getProductId());
                ps2.setLong(3, r.getProductId());
                ps2.executeUpdate();
            }

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                // trả auto-commit về true (an toàn)
                try { conn.setAutoCommit(true); } catch (Exception ignore) {}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    

    
