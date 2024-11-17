package org.example.demo6;

import org.example.demo6.Classes.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PathCheck {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:database//LibraryMain"; // Đường dẫn đến file SQLite của bạn
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        String username = "admin";
        String password = "123";

        User user = null;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("DATE_OF_BIRTH"));
                System.out.println(dateOfBirth.getDayOfMonth());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
