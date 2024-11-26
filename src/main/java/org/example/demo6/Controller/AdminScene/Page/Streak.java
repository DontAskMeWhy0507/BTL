package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.Library;

import java.util.Arrays;
import java.util.List;

public class Streak {
    DBUltis dbUltis = new DBUltis();
    Library library = Library.getInstance();

    @FXML
    private ProgressBar streakProgressBar;

    @FXML
    private Label streakLabel;

    @FXML
    private Label longestStreakLabel;

    @FXML
    private Label milestoneLabel;

    @FXML
    private ListView<HBox> achievedRewardsList;

    // Streak data
    private int currentStreak = library.getCurrentUser().getStreak().getStreak();
    private int longestStreak = library.getCurrentUser().getStreak().getLongestStreak();

    @FXML
    private void initialize() {
        updateStreakDetails();
    }

    // them phan thuong
    private void addRewardImage(String imagePath) {
        HBox hBox = new HBox();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        hBox.getChildren().add(imageView);
        achievedRewardsList.getItems().add(hBox);
    }

    // chinh streak
    private void updateStreakDetails() {
        streakLabel.setText("Chuỗi hiện tại: " + currentStreak + " ngày");
        longestStreakLabel.setText("Chuỗi dài nhất: " + longestStreak + " ngày");

        int nextMilestone = Library.getInstance().getCurrentUser().getStreak().getNextMilestone();
        if (nextMilestone != -1) {
            double progress = (double) currentStreak / nextMilestone;
            streakProgressBar.setProgress(Math.min(1.0, progress));
            milestoneLabel.setText("Mốc tiếp theo: " + nextMilestone + " ngày");
        } else {
            streakProgressBar.setProgress(1.0);
            milestoneLabel.setText("Congratulations! You've reached the highest milestone.");
        }

        achievedRewardsList.getItems().clear();
        if (longestStreak > 7) {
            addRewardImage("/Image/Avatar/Skye1.png");
        }
        if (longestStreak > 10) {
            addRewardImage("/Image/Avatar/Raze2.png");
        }
        if (longestStreak > 30) {
            addRewardImage("/Image/Avatar/Killjoy3.png");
        }
    }
}
