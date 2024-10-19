package org.example.demo6;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainSceneClass {
    @FXML
    private AnchorPane mainContent;

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HomePage.fxml"));
            Parent homeView = loader.load();
            mainContent.getChildren().clear();
            mainContent.getChildren().add(homeView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSubscriptions() {
        // Hiển thị nội dung Subscriptions
        mainContent.getChildren().clear();
        mainContent.getChildren().add(new Label("Subscriptions Content"));
    }


    @FXML
    private Button avatarButton;




}
