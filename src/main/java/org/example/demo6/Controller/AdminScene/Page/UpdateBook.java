package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.demo6.Classes.Admin;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class UpdateBook extends PageUploadController {
    Library library = Library.getInstance();
    DBUltis dbUltis = new DBUltis();
    Book book;

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
    public void initialize() {
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
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to upload this file and save the book details?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    validateFields();
                    // Lấy dữ liệu từ các trường trong giao diện
                    String isbn = ISBN.getText();
                    String title = Tittle.getText();
                    String author = Authors.getText();
                    String category = Categories.getText();
                    String description = Description.getText();
                    String language = Language.getText();
                    String publisher = Publisher.getText();
                    LocalDate publishedDate;
                    try {
                        publishedDate = PublishedDate.getValue();
                        if (publishedDate == null) {
                            throw new IllegalArgumentException("Published date is required.");
                        }
                    } catch (DateTimeParseException e) {
                        throw new IllegalArgumentException("Invalid published date format.");
                    }
                    int quantity;
                    try {
                        quantity = Integer.parseInt(this.quantity.getText());
                        if (quantity < 0) {
                            throw new IllegalArgumentException("Quantity cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid quantity format.");
                    }

                    String bookPath = (selectedFile != null) ? "/Uploaded/Books/" + selectedFile.getName() : null;
                    String coverImagePath = (selectedImageFile != null) ? "/Uploaded/BookCovers/" + selectedImageFile.getName() : null;
                    String audioPathIfHave = (selectedAudioFile != null) ? "/Uploaded/AudioBooks/" + selectedAudioFile.getName() : null;

                    // Tạo đối tượng Book
                    Book newBook = new Book(isbn, title, author, category, description, language, publisher, publishedDate, bookPath, coverImagePath, audioPathIfHave, quantity);

                    // Kiểm tra trùng lặp
                    checkDuplicateBook(isbn);

                    if (library.getCurrentUser() instanceof Admin) {
                        ((Admin) library.getCurrentUser()).upLoadBook(newBook, selectedFile, selectedImageFile, selectedAudioFile);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Success");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Book uploaded successfully.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Current user is not an admin.");
                        errorAlert.showAndWait();
                        return;
                    }

                    // Thực hiện các thao tác khác với đối tượng Book (lưu vào cơ sở dữ liệu, hiển thị, ...)
                } catch (IllegalArgumentException e) {
                    showAlert("Warning", e.getMessage());
                } catch (Exception e) {
                    showAlert("Error", "An unexpected error occurred: " + e.getMessage());
                }
            }
        });
    }

    public void validateFields() throws IllegalArgumentException {
        if (ISBN.getText().isEmpty()) {
            throw new IllegalArgumentException("ISBN field is empty");
        }
        if (Tittle.getText().isEmpty()) {
            throw new IllegalArgumentException("Title field is empty");
        }
        if (Authors.getText().isEmpty()) {
            throw new IllegalArgumentException("Authors field is empty");
        }
        if (Categories.getText().isEmpty()) {
            throw new IllegalArgumentException("Categories field is empty");
        }
        if (Description.getText().isEmpty()) {
            throw new IllegalArgumentException("Description field is empty");
        }
        if (Language.getText().isEmpty()) {
            throw new IllegalArgumentException("Language field is empty");
        }
        if (Publisher.getText().isEmpty()) {
            throw new IllegalArgumentException("Publisher field is empty");
        }
        if (PublishedDate.getValue() == null) {
            throw new IllegalArgumentException("Published date must be selected");
        }
        if (quantity.getText().isEmpty()) {
            throw new IllegalArgumentException("Quantity field is empty");
        }
    }

    public void checkDuplicateBook(String isbn) throws Exception {
        if (dbUltis.findQuery("SELECT * FROM books WHERE isbn = '" + isbn + "'" + "AND isbn != " + book.getIsbn())) {
            throw new Exception("ISBN is already taken");
        }
    }

    // Phương thức hiển thị thông báo
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setBookData(Book book) {
        this.book = book;
        ISBN.setText(book.getIsbn());
        Tittle.setText(book.getTitle());
        Authors.setText(book.getAuthor());
        Categories.setText(book.getCategory());
        Description.setText(book.getDescription());
        Language.setText(book.getLanguage());
        Publisher.setText(book.getPublisher());
        PublishedDate.setValue(book.getPublishedDate());
    }

    public void setBookDb(Book book) {
        this.book = book;
        ISBN.setText(book.getIsbn());
        Tittle.setText(book.getTitle());
        Authors.setText(book.getAuthor());
        Categories.setText(book.getCategory());
        Description.setText(book.getDescription());
        Language.setText(book.getLanguage());
        Publisher.setText(book.getPublisher());
        PublishedDate.setValue(book.getPublishedDate());
        quantity.setText(String.valueOf(book.getQuantity()));
        try {
            String coverImageUrl = book.getCoverImagePath();
            if (coverImageUrl != null && !coverImageUrl.isEmpty()) {
                BookCover.setImage(new javafx.scene.image.Image(coverImageUrl));
            } else {
                System.err.println("Cover image URL is null or empty.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URL for book cover image: " + book.getCoverImagePath());
        }
    }
}
