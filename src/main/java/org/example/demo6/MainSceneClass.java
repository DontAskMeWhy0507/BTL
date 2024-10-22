package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainSceneClass {

    @FXML
    private TextField SearchField;

    @FXML
    private VBox seeMoreProfile;
    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private Label welcomeText;

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void searchButton() throws IOException {
        apiGoogleBooks.searchBooks(SearchField.getText());
    }

    @FXML
    void moreButton() {
       seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }



    public void initialize() {
        // Bạn có thể tùy chỉnh tốc độ cuộn hoặc các thiết lập khác cho ScrollPane ở đây nếu cần
        mainScrollPane.setFitToWidth(true);  // Để nội dung khớp theo chiều rộng của ScrollPane
        mainScrollPane.setFitToHeight(true); // Để nội dung khớp theo chiều cao của ScrollPane (nếu cần)
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PageHome.fxml"));
            Parent homeView = loader.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(homeView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSubscriptions() {
        // Hiển thị nội dung Subscriptions trong ScrollPane
        mainScrollPane.setContent(new Label("Subscriptions Content"));
    }
    public void logOut(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DBUltis.changescene(event, "/View/Login.fxml", "Login!");
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
    }
}
