package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.example.demo6.Classes.*;

import java.util.ArrayList;

public class Statistics {
    DBUltis dbUltis = new DBUltis();
    @FXML
    private PieChart bookCategoryChart;

    @FXML
    private BarChart<String, Number> userStreakBarChart;

    @FXML
    public void initialize() {
        loadPieChartData();
        loadBarChartData();
    }

    // dữ liệu biểu đồ tròn
    private void loadPieChartData() {
        ArrayList<Book> books = dbUltis.getBooksFromDatabase();
        while (books.size() > 0) {
            Book book = books.get(0);
            int count = 0;
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getCategory().equals(book.getCategory())) {
                    count++;
                    books.remove(i);
                    i--;
                }
            }
            bookCategoryChart.getData().add(new PieChart.Data(book.getCategory(), count));
        }
    }

    // dữ liệu biểu đồ cột
    private void loadBarChartData() {
        XYChart.Series<String, Number> streakSeries = new XYChart.Series<>();
        streakSeries.setName("Top User Streak");

        Library library = Library.getInstance();
        if (library.getCurrentUser() instanceof Admin) {
            Admin admin = (Admin) library.getCurrentUser();
            for (User user : admin.getUsers()) {
                streakSeries.getData().add(new XYChart.Data<>(user.getUsername(), user.getIntStreak()));
            }
        } else {
            System.err.println("Current user is not an admin.");
        }

        userStreakBarChart.getData().add(streakSeries);
    }
}
