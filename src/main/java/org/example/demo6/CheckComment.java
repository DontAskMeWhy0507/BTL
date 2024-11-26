package org.example.demo6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckComment extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Tải file fxml
        Parent root = FXMLLoader.load(getClass().getResource("/View/UserScene/Page/CommentItem.fxml"));

        // scene với fxml
        Scene scene = new Scene(root);

        // scene vào stage
        stage.setScene(scene);

        // tên stage
        stage.setTitle("Comment Item");

        // hiển thị stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}