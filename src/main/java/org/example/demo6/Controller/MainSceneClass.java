package org.example.demo6.Controller;

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
import org.example.demo6.DBUltis;
import org.example.demo6.apiGoogleBooks;

import java.io.IOException;
import java.util.Optional;

public class MainSceneClass {

    @FXML
    private TextField SearchField;
    @FXML
    private ListView<String> suggestionsList;

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

    private static ScrollPane staticMainScrollPane;

    @FXML
    public void initialize() {
        staticMainScrollPane = mainScrollPane;
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setFitToHeight(true);
        showHome();
    }

    public static void setMainContent(Parent content) {
        staticMainScrollPane.setContent(content);
    }

    @FXML
    void searchButton() throws IOException {
        apiGoogleBooks.searchBooks(SearchField.getText());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PageSearch.fxml"));
            Parent homeView = loader.load();
            setMainContent(homeView);
            staticMainScrollPane.setFitToWidth(true);
            staticMainScrollPane.setFitToHeight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void moreButton() {
        seeMoreProfile.setVisible(!seeMoreProfile.isVisible());
    }

    public void showHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Page/Home.fxml"));
            Parent homeView = loader.load();
            setMainContent(homeView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAll() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Page/All.fxml"));
            Parent homeView = loader.load();

            // Get the controller instance
            AllPageController controller = loader.getController();

            // Optionally: you can call a method to set up data if needed
            // controller.initialize();

            // Set the content of the ScrollPane (or the appropriate parent node)
            mainScrollPane.setContent(homeView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    public void showUpload() {
        try {

            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/View/PageUpload.fxml"));
            Parent uploadView = loader1.load();
            setMainContent(uploadView);
          
          

            System.out.println("Upload");
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/View/Page/Upload.fxml"));
            Parent UploadView = loader2.load();

            // Đặt nội dung mới vào ScrollPane
            mainScrollPane.setContent(UploadView);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showSubscriptions() {
        setMainContent(new Label("Subscriptions Content"));
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



