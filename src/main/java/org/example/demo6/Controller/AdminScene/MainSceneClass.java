package org.example.demo6.Controller.AdminScene;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.*;
import org.example.demo6.Controller.AdminScene.Page.ChatAIController;
import org.example.demo6.Controller.AdminScene.Page.SearchPageController;
import javafx.scene.image.Image;
import org.example.demo6.Controller.AdminScene.Page.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.demo6.Controller.GeneralController.changescene;

public class MainSceneClass {
    DBUltis DBUltis = new DBUltis();

    // Singleton
    private static MainSceneClass instance;
    Library library = Library.getInstance();

    @FXML
    private TextField SearchField;

    @FXML
    private VBox seeMoreProfile;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private Button avatarButton;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView avatar;

    @FXML
    private ProgressIndicator loadingIndicator;

    @FXML
    private Label userName;

    @FXML
    private ImageView sound;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private boolean isMuted = false;

    private Music music;

    public static ScrollPane staticMainScrollPane;

    @FXML
    public void initialize() {
        sound.setImage(new Image(String.valueOf(getClass().getResource("/Image/Icon/volume.png"))));
        music = Music.getInstance();
        volumeSlider.setValue(50);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            setVolume(newValue.doubleValue());
        });

        setUser();
        staticMainScrollPane = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        changeToUserView();
    }

    @FXML
    void searchButton(ActionEvent event) {
        String searchQuery = SearchField.getText();

        // Hiển thị trạng thái tải trong khi tìm kiếm
        loadingIndicator.setVisible(true);
        loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

        // Tạo task cho việc tìm kiếm sách (API và Database)
        Task<List<List<Book>>> task = new Task<>() {
            @Override
            protected List<List<Book>> call() throws IOException {
                // Gọi API và tìm kiếm từ cơ sở dữ liệu
                List<Book> apiResult = library.getCurrentUser().searchBooksApi(searchQuery);
                List<Book> databaseResult = library.getCurrentUser().searchBooksDatabase(searchQuery);

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/PageSearch.fxml"));
                Parent homeView = loader.load();

                // Lấy controller và truyền kết quả tìm kiếm
                SearchPageController searchPageController = loader.getController();
                searchPageController.setSearchResults(databaseResult, apiResult);

                setMainContent(homeView);
                staticMainScrollPane.setFitToWidth(true);
                staticMainScrollPane.setFitToHeight(true);
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

    // Method to update the avatar
    public void changeAvatar(String avatarPaths) {
        Image newAvatarImage = new Image(getClass().getResourceAsStream(avatarPaths));
        avatar.setImage(newAvatarImage);
    }

    public static void setMainContent(Parent content) {
        staticMainScrollPane.setContent(content);
    }

    @FXML
    void moreButton() {
        seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }

    @FXML
    void changeToUserView() {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/StatisticsView.fxml"));
            Parent UserView = loader1.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(UserView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showSettings(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Settings.fxml"));
            Parent SettingView = loader1.load();
            Settings settingsController = loader1.getController();

            // Truyền đối tượng MainSceneClass vào SettingsController
            settingsController.setMainSceneController(this);
            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(SettingView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void EnterToSearch(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            searchButton(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }

    @FXML
    public void ChatAI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/ChatAI.fxml"));
            Parent homeView = loader.load();

            // Get the controller instance
            ChatAIController chatAIController = loader.getController();

            setMainContent(homeView);
            staticMainScrollPane.setFitToWidth(true);
            staticMainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainSceneClass getInstance() {
        if (instance == null) {
            instance = new MainSceneClass();
        }
        return instance;
    }

    public void setUser() {
        changeAvatar(Library.getInstance().getCurrentUser().getPathToProfilePicture());
        avatarButton.setText(Library.getInstance().getCurrentUser().getUsername());
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/MemberTable.fxml"));
            Parent homeView = loader.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(homeView);
            staticMainScrollPane = mainScrollPane;
            mainScrollPane.setFitToWidth(true);
            mainScrollPane.setFitToHeight(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAll() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/BookManageTable.fxml"));
            Parent homeView = loader.load();


            mainScrollPane.setContent(homeView);
            staticMainScrollPane = mainScrollPane;
            mainScrollPane.setFitToWidth(true);
            mainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void showUpload() {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/Upload.fxml"));
            Parent UploadView = loader1.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(UploadView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Library.getInstance().logOut(event);
            } catch (Exception e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause != null) {
                    cause.printStackTrace();
                }
            }
        }
    }

    // Phương thức điều chỉnh âm lượng
    public void setVolume(double volume) {
        music.setVolume(volume / 100);
    }

    public void muteSound(ActionEvent event) {
        isMuted = !isMuted;

        if (isMuted) {
            volumeSlider.setValue(0);
            sound.setImage(new Image(String.valueOf(getClass().getResource("/Image/Icon/mute.png"))));
        } else {
            volumeSlider.setValue(50);
            sound.setImage(new Image(String.valueOf(getClass().getResource("/Image/Icon/volume.png"))));
        }
    }
}
