package org.example.demo6.Controller.AdminScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo6.Classes.Book;
import org.example.demo6.Controller.AdminScene.Page.SearchPageController;
import org.example.demo6.Classes.DBUltis;
import org.example.demo6.Classes.apiGoogleBooks;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class MainSceneClass{

    @FXML
    private TextField SearchField;

    @FXML
    private VBox seeMoreProfile;
    @FXML
    private ScrollPane mainScrollPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private static ScrollPane staticMainScrollPane;
    @FXML
    void searchButton() throws IOException {
        List<Book> searchResults = apiGoogleBooks.searchBooks1(SearchField.getText());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/PageSearch.fxml"));
            Parent homeView = loader.load();

            // Get the controller instance
            SearchPageController searchPageController = loader.getController();
            searchPageController.setSearchResults(searchResults);

            setMainContent(homeView);
            staticMainScrollPane.setFitToWidth(true);
            staticMainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMainContent(Parent content) {
        staticMainScrollPane.setContent(content);
    }



    @FXML
    void moreButton() {
       seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }



    @FXML
    public void initialize() {
        staticMainScrollPane = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        showHome();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AdminScene/Page/All.fxml"));
            Parent homeView = loader.load();


            setMainContent(homeView);
            staticMainScrollPane.setFitToWidth(true);
            staticMainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showUpload() {
        try {
            System.out.println("Upload");
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
                DBUltis.changescene(event, "/View/LoginScene/Login.fxml", "Login!");
            } catch (Exception e) {
                e.printStackTrace();
                Throwable cause = e.getCause();
                if (cause != null) {
                    cause.printStackTrace();
                }
            }
        }
    }


    @FXML
    void changeToUserView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/UserScene/MainSceneUser.fxml"));
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

    @FXML
    void showSettings(ActionEvent event) {

    }






}
