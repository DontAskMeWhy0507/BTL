package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.demo6.Book;
import org.example.demo6.DBUltis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class PageUploadController {

    @FXML
    private TextField Authors;

    @FXML
    private TextField Categories;

    @FXML
    private TextField Description;

    @FXML
    private TextField Language;

    @FXML
    private DatePicker PublishedDate;

    @FXML
    private TextField Publisher;

    @FXML
    private TextField Tittle;

    @FXML
    private ImageView BookCover;

    @FXML
    private Button uploadButton;

    @FXML
    private Button SelectCover;

    @FXML
    private Button AudioBook;

    @FXML
    private Button Confirm;

    // Thư mục đích để lưu file
    private static final String UPLOAD_DIRECTORY = "Uploaded/";

    // Biến lưu trữ file đã chọn tạm thời
    private File selectedFile;

    @FXML
    private void initialize() {
        // Thiết lập hành động khi nhấn nút upload
        uploadButton.setOnAction(event -> {
            // Tạo FileChooser để chọn file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File to Upload");

            // Mở hộp thoại chọn file
            Stage stage = (Stage) uploadButton.getScene().getWindow();
            selectedFile = fileChooser.showOpenDialog(stage);

            // Nếu người dùng chọn file, hiển thị tên file
            if (selectedFile != null) {
                Tittle.setText(selectedFile.getName());
            }
        });

        SelectCover.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image as Cover");

            // Chỉ cho phép chọn file ảnh
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            Stage stage = (Stage) SelectCover.getScene().getWindow();
            File selectedImageFile = fileChooser.showOpenDialog(stage);

            if (selectedImageFile != null) {
                // Hiển thị ảnh trong ImageView
                BookCover.setImage(new javafx.scene.image.Image(selectedImageFile.toURI().toString()));
            }
        });

        Confirm.setOnAction(event -> {
            if (selectedFile != null) {
                // Hiển thị hộp thoại xác nhận
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Are you sure you want to upload this file and save the book details?");

                Optional<ButtonType> result = confirmAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Nếu người dùng chọn OK, lưu file và tạo đối tượng Book
                    saveFileToFolder(selectedFile);

                    // Lấy dữ liệu từ các trường trong giao diện
                    String title = Tittle.getText();
                    String author = Authors.getText();
                    String category = Categories.getText();
                    String description = Description.getText();
                    String language = Language.getText();
                    String publisher = Publisher.getText();
                    String publishedDate = (PublishedDate.getValue() != null) ? PublishedDate.getValue().toString() : null;
                    String coverImagePath = selectedFile.getAbsolutePath(); // Đường dẫn tới file ảnh bìa

                    // Tạo đối tượng Book
                    Book newBook = new Book(title, author, category, description, language, publisher, publishedDate, coverImagePath);
                    DBUltis.saveBookToDatabase(newBook);
                    // Thực hiện các thao tác khác với đối tượng Book (lưu vào cơ sở dữ liệu, hiển thị, ...)
                    System.out.println("Book created: " + newBook.getTitle());
                }
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
