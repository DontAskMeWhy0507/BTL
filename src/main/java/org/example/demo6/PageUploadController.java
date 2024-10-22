package org.example.demo6;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class PageUploadController {

    @FXML
    private Button uploadButton;

    // Thư mục đích để lưu file
    private static final String UPLOAD_DIRECTORY = "Uploaded/";

    @FXML
    private void initialize() {
        // Thiết lập hành động khi nhấn nút upload
        uploadButton.setOnAction(event -> {
            // Tạo FileChooser để chọn file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File to Upload");

            // Mở hộp thoại chọn file
            Stage stage = (Stage) uploadButton.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            // Nếu người dùng chọn file, tiến hành lưu file
            if (selectedFile != null) {
                saveFileToFolder(selectedFile);
            }
        });
    }

    // Phương thức lưu file vào thư mục 'uploads'
    private void saveFileToFolder(File file) {
        // Tạo thư mục đích nếu nó chưa tồn tại
        File dir = new File(UPLOAD_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        // Đường dẫn đích để lưu file
        Path destinationPath = new File(dir, file.getName()).toPath();

        try {
            // Sao chép file vào thư mục đích
            Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved to: " + destinationPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save the file.");
        }
    }
}
