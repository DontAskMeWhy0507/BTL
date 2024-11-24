package org.example.demo6.Controller.UserScene;

import com.google.errorprone.annotations.FormatMethod;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.*;
import org.example.demo6.Controller.UserScene.Page.SearchPageController;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.demo6.Controller.AdminScene.MainSceneClass.staticMainScrollPane;
import static org.example.demo6.Controller.GeneralController.changescene;

public class MainSceneUser {
    private Stage stage;
    private Scene scene;
    private Parent root;

    DBUltis DBUltis = new DBUltis();

    @FXML
    private TextField SearchField;

    @FXML
    private Button avatarButton;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox seeMoreProfile;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView avatar;

    @FXML
    private ProgressIndicator loadingIndicator;

    private Music music;

    public static ScrollPane staticMainScrollPane1;

    public static void setMainContent(Parent content) {
        staticMainScrollPane1.setContent(content);
    }

    // Phương thức điều chỉnh âm lượng
    private void setVolume(double volume) {
        music.setVolume(volume / 100);
    }

    @FXML
    void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                changescene(event, "/View/LoginScene/Login.fxml", "Login!");
            } catch (Exception e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause != null) {
                    cause.printStackTrace();
                }
            }
        }

    }

    @FXML
    void changeToChatAI() {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/UserScene/Page/ChatAI.fxml"));
            Parent ChatAIView = loader1.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(ChatAIView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void moreButton() {
        seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }

    @FXML
    public void searchButton(ActionEvent event) throws IOException {
        String searchQuery = SearchField.getText();

        // Hiển thị trạng thái tải trong khi tìm kiếm
        loadingIndicator.setVisible(true);
        loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        // Tạo task cho việc tìm kiếm sách (API và Database)
        Task<List<List<Book>>> task = new Task<>() {
            @Override
            protected List<List<Book>> call() throws IOException {
                // Gọi API và tìm kiếm từ cơ sở dữ liệu
                List<Book> apiResult = apiGoogleBooks.searchBooks1(searchQuery);
                List<Book> databaseResult = DBUltis.searchBook(searchQuery);

                // Trả về kết quả dưới dạng một danh sách chứa cả hai kết quả
                List<List<Book>> result = new ArrayList<>();
                result.add(databaseResult);
                result.add(apiResult);
                return result;
            }
        };

        // Xử lý khi task hoàn thành
        task.setOnSucceeded(event1 -> {
            loadingIndicator.setVisible(false); // Ẩn progress indicator khi hoàn thành

            // Lấy kết quả từ task
            List<Book> databaseResult = task.getValue().get(0);
            List<Book> apiResult = task.getValue().get(1);

            try {
                // Tải và hiển thị trang kết quả tìm kiếm
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/PageSearch.fxml"));
                Parent homeView = loader.load();

                // Lấy controller và truyền kết quả tìm kiếm
                SearchPageController searchPageController = loader.getController();
                searchPageController.setSearchResults(databaseResult, apiResult);

                setMainContent(homeView);
                staticMainScrollPane1.setFitToWidth(true);
                staticMainScrollPane1.setFitToHeight(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Xử lý nếu task thất bại
        task.setOnFailed(event1 -> {
            loadingIndicator.setVisible(false); // Ẩn progress indicator nếu có lỗi
            Throwable error = task.getException();
            System.err.println("Error while searching: " + error.getMessage());
        });

        // Chạy task trong một thread mới
        new Thread(task).start();
    }


    public void changeAvatar(String avatarPaths) {
        Image newAvatarImage = new Image(getClass().getResourceAsStream(avatarPaths));
        avatar.setImage(newAvatarImage);
    }

    public void setUser() {
        changeAvatar(Library.getInstance().getCurrentUser().getPathToProfilePicture());
        avatarButton.setText(Library.getInstance().getCurrentUser().getUsername());
    }

    public void showAll() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/All.fxml"));
            Parent homeView = loader.load();


            setMainContent(homeView);
            staticMainScrollPane1.setFitToWidth(true);
            staticMainScrollPane1.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize() {
        music = Music.getInstance();
        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setVolume(newValue.doubleValue());
        });
        setUser();
        staticMainScrollPane1 = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        showHome();
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Home.fxml"));
            Parent homeView = loader.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(homeView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void showSettings(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/UserScene/Page/Settings.fxml"));
            Parent SettingView = loader1.load();
            org.example.demo6.Controller.UserScene.Page.Settings settingsController = loader1.getController();

            // Create or get an instance of MainSceneUser
            MainSceneUser mainSceneUserInstance = this;

            // Pass the instance to the setMainSceneController method
            settingsController.setMainSceneController(mainSceneUserInstance);

            // Set the new content in the ScrollPane
            mainScrollPane.setContent(SettingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void changeAdminView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/AdminScene/MainScene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                cause.printStackTrace();
            }
        }
    }


    public void changeToStreak() {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Streak.fxml"));
            Parent StreakView = loader1.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(StreakView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
