package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.example.demo6.Classes.Library;
import org.example.demo6.Classes.User;  // Giả sử bạn có class User để lấy thông tin về người dùng

public class Statistics {
    @FXML
    private PieChart bookCategoryChart;

    @FXML
    private BarChart<String, Number> userStreakBarChart;

    @FXML
    public void initialize() {
        // Load statistics from the library
        loadPieChartData();
        loadBarChartData();
    }

    // Load data for PieChart (book categories borrowed)
    private void loadPieChartData() {
        // Example data for book categories
        bookCategoryChart.getData().addAll(
                new PieChart.Data("Fiction", 150),
                new PieChart.Data("Non-fiction", 80),
                new PieChart.Data("Science", 70),
                new PieChart.Data("History", 50)
        );
    }

    // Load data for BarChart (top user streaks)
    private void loadBarChartData() {
        // Create a data series for the bar chart
        XYChart.Series<String, Number> streakSeries = new XYChart.Series<>();
        streakSeries.setName("Top User Streak");

        // Get the users from the library
        Library library = Library.getInstance();

        // Giả sử bạn có danh sách người dùng và mỗi người có số ngày streak
        for (User user : library.getUsers()) {
            streakSeries.getData().add(new XYChart.Data<>(user.getUsername(), user.getStreakDays()));
        }

        // Add data series to the BarChart
        userStreakBarChart.getData().add(streakSeries);
    }
}
