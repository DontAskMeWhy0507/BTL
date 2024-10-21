package org.example.demo6;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainSceneClass {

    @FXML
    private TextField SearchField;


    @FXML
    private Button avatarButton;

    @FXML
    private VBox seeMoreProfile;


    @FXML
    void moreButton() {
       seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }
    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private AnchorPane mainContent;

    public void initialize() {
        // Bạn có thể tùy chỉnh tốc độ cuộn hoặc các thiết lập khác cho ScrollPane ở đây nếu cần
        mainScrollPane.setFitToWidth(true);  // Để nội dung khớp theo chiều rộng của ScrollPane
        mainScrollPane.setFitToHeight(true); // Để nội dung khớp theo chiều cao của ScrollPane (nếu cần)
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HomePage.fxml"));
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
}
