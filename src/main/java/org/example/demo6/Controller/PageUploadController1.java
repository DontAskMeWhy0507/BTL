package org.example.demo6.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.demo6.Classes.Book;
import org.example.demo6.DBUltis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class PageUploadController1 {
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
    private TextField TextField_count;

    @FXML
    private Button Confirm;

    @FXML
    private TextField ISBN;

    // Thư mục đích để lưu file
    private static final String UPLOAD_DIRECTORY_BOOKS = "Uploaded/Books";
    private static final String UPLOAD_DIRECTORY_AUDIO = "Uploaded/AudioBooks";
    private static final String UPLOAD_DIRECTORY_IMAGE = "Uploaded/BookCovers";

    // Biến lưu trữ file đã chọn tạm thời
    private File selectedFile;
    private File selectedImageFile;
    private File selectedAudioFile;

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
            selectedImageFile = fileChooser.showOpenDialog(stage);

            if (selectedImageFile != null) {
                // Hiển thị ảnh trong ImageView
                BookCover.setImage(new javafx.scene.image.Image(selectedImageFile.toURI().toString()));
            }
        });

        AudioBook.setOnAction(event-> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Audio Book");

            // Chỉ cho phép chọn file audio
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav")
            );

            Stage stage = (Stage) AudioBook.getScene().getWindow();
            selectedAudioFile = fileChooser.showOpenDialog(stage);


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
                    saveFileToFolder(selectedFile, "BOOKS");
                    if (selectedImageFile != null) {
                        saveFileToFolder(selectedImageFile, "IMAGE");
                    }
                    if (selectedAudioFile != null) {
                        saveFileToFolder(selectedAudioFile, "AUDIO");
                    }
                    // Lấy dữ liệu từ các trường trong giao diện
                    String isbn = ISBN.getText();
                    String title = Tittle.getText();
                    String author = Authors.getText();
                    String category = Categories.getText();
                    String description = Description.getText();
                    String language = Language.getText();
                    String publisher = Publisher.getText();
                    String publishedDate = (PublishedDate.getValue() != null) ? PublishedDate.getValue().toString() : null;
                    int count = Integer.parseInt(TextField_count.getText());

                    String coverImagePath = (selectedImageFile != null) ? "/Uploaded/BookCovers/" + selectedImageFile.getName() : null;
                    String audioPathIfHave = (selectedAudioFile != null) ? "/Uploaded/AudioBooks/" + selectedAudioFile.getName() : null;
                    // Tạo đối tượng Book
                    Book newBook = new Book(isbn, title, author, category, description, language, publisher, publishedDate,null,  coverImagePath, audioPathIfHave, count);
                    // Lưu đối tượng Book vào cơ sở dữ liệu
                    DBUltis.saveBookToDatabase(newBook);

                    // Thực hiện các thao tác khác với đối tượng Book (lưu vào cơ sở dữ liệu, hiển thị, ...)
                    System.out.println("Book created: " + newBook.getTitle());
                }
            }

        });

    }

    // Phương thức lưu file vào thư mục 'uploads'
    private void saveFileToFolder(File file, String type) {
        String directoryPath;
        switch (type) {
            case "BOOKS":
                directoryPath = UPLOAD_DIRECTORY_BOOKS;
                break;
            case "IMAGE":
                directoryPath = UPLOAD_DIRECTORY_IMAGE;
                break;
            case "AUDIO":
                directoryPath = UPLOAD_DIRECTORY_AUDIO;
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

        // Tạo thư mục đích nếu nó chưa tồn tại
        File dir = new File(directoryPath);
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

    public void setBookData(Book book) {
        Tittle.setText(book.getTitle());
        Authors.setText(book.getAuthor());
        Publisher.setText(book.getPublisher());
        Categories.setText(book.getCategory());
        Language.setText(book.getLanguage());
        Description.setText(book.getDescription());
        ISBN.setText(book.getIsbn());

        // Set the published date if available
        if (book.getPublishedDate() != null && !book.getPublishedDate().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                PublishedDate.setValue(LocalDate.parse(book.getPublishedDate(), formatter));
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + e.getMessage());
            }
        }

    }

}

