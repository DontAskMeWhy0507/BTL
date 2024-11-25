package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.demo6.Classes.Admin;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.Library;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class PageUploadController {
    Library library = Library.getInstance();

    @FXML
    private TextField Authors;

    @FXML
    private TextField quantity;

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

    @FXML
    private TextField ISBN;


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

                    // Lấy dữ liệu từ các trường trong giao diện
                    String isbn = ISBN.getText();
                    String title = Tittle.getText();
                    String author = Authors.getText();
                    String category = Categories.getText();
                    String description = Description.getText();
                    String language = Language.getText();
                    String publisher = Publisher.getText();
                    LocalDate publishedDate = PublishedDate.getValue();
                    int quantity = Integer.parseInt(this.quantity.getText());

                    String bookPath = "/Uploaded/Books/" + selectedFile.getName();
                    String coverImagePath = (selectedImageFile != null) ? "/Uploaded/BookCovers/" + selectedImageFile.getName() : null;
                    String audioPathIfHave = (selectedAudioFile != null) ? "/Uploaded/AudioBooks/" + selectedAudioFile.getName() : null;
                    // Tạo đối tượng Book
                    Book newBook = new Book(isbn, title, author, category, description, language, publisher, publishedDate,bookPath ,coverImagePath, audioPathIfHave,quantity);

                    if (library.getCurrentUser() instanceof Admin) {
                        ((Admin) library.getCurrentUser()).upLoadBook(newBook, selectedFile, selectedImageFile, selectedAudioFile);
                    } else {
                        System.err.println("Current user is not an admin.");
                    }

                    // Thực hiện các thao tác khác với đối tượng Book (lưu vào cơ sở dữ liệu, hiển thị, ...)
                    System.out.println("Book created: " + newBook.getTitle());
                }
            }

        });

    }

    public void setBookData(Book book) {
        ISBN.setText(book.getIsbn());
        Tittle.setText(book.getTitle());
        Authors.setText(book.getAuthor());
        Categories.setText(book.getCategory());
        Description.setText(book.getDescription());
        Language.setText(book.getLanguage());
        Publisher.setText(book.getPublisher());
        PublishedDate.setValue(book.getPublishedDate());
    }
}

