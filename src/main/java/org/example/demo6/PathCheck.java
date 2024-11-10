package org.example.demo6;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathCheck {
    public static void main(String[] args) {
        String filePath = "../Uploaded/BookCovers/";

        try {
            // Thử tạo đường dẫn
            Path path = Paths.get(filePath);
            System.out.println("Đường dẫn hợp lệ: " + path.toAbsolutePath());
        } catch (Exception e) {
            // Báo lỗi nếu đường dẫn không hợp lệ
            System.err.println("Đường dẫn không hợp lệ: " + e.getMessage());
        }
    }
}
