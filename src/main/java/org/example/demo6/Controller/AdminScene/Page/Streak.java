package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import org.example.demo6.Classes.Library;

import java.util.Arrays;
import java.util.List;

public class Streak {

    @FXML
    private ProgressBar streakProgressBar;

    @FXML
    private Label streakLabel;

    @FXML
    private Label longestStreakLabel;

    @FXML
    private Label milestoneLabel;

    @FXML
    private Label rewardLabel;  // Thêm label để hiển thị phần thưởng


    // Streak data

    private int currentStreak = Library.getInstance().getCurrentUser().getStreak().getStreak();
    private int longestStreak = Library.getInstance().getCurrentUser().getStreak().getLongestStreak();

    @FXML
    private void initialize() {
        // Initialize UI components with streak data
        updateStreakDetails();

    }

    private void updateStreakDetails() {
        // Update labels
        streakLabel.setText("Chuỗi hiện tại: " + currentStreak + " ngày");
        longestStreakLabel.setText("Chuỗi dài nhất: " + longestStreak + " ngày");

        // Calculate progress to the next milestone
        int nextMilestone = Library.getInstance().getCurrentUser().getStreak().getNextMilestone();
        if (nextMilestone != -1) {
            double progress = (double) currentStreak / nextMilestone;
            streakProgressBar.setProgress(Math.min(1.0, progress));
            milestoneLabel.setText("Next Milestone: " + nextMilestone + " ngày");
        } else {
            // If no milestone is left, show maxed-out progress
            streakProgressBar.setProgress(1.0);
            milestoneLabel.setText("Congratulations! You've reached the highest milestone.");
        }
    }


}
