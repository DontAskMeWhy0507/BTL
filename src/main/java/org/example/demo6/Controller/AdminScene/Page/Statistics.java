package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.example.demo6.Classes.Library;

public class Statistics {
    @FXML
    private PieChart bookCategoryChart;

    @FXML
    private BarChart<String, Number> userBookBarChart;

    @FXML
    public void initialize() {
        // Load statistics from the library
        loadPieChartData();
        loadBarChartData();
    }

    private void loadPieChartData() {
        // Example data for book categories
        bookCategoryChart.getData().addAll(
                new PieChart.Data("Fiction", 150),
                new PieChart.Data("Non-fiction", 80),
                new PieChart.Data("Science", 70),
                new PieChart.Data("History", 50)
        );
    }

    private void loadBarChartData() {
        // Example data for the bar chart
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Library Data");

        Library library = Library.getInstance();
        //dataSeries.getData().add(new XYChart.Data<>("Books", library.getBooks().size()));
        //dataSeries.getData().add(new XYChart.Data<>("Users", library.getUsers().size()));

        userBookBarChart.getData().add(dataSeries);
    }
}
