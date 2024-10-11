package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene1(ActionEvent event) {
        root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource().getScene()
    }

    public void switchToScene2(ActionEvent event) {

    }

    /**
     * Start button
     */
    @FXML
    private Label welcomeText1;

    @FXML
    protected void onAbc123() {
        welcomeText1.setText("Welcome Text1!");
    }
    /**
     * Select thing from box
     */
    @FXML
    private Label myLabel;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String[] food = {"midomi", "com", "pho"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        myChoiceBox.getItems().addAll(food);
    }

    /**
     * DatePicker
     */
    @FXML
    private DatePicker datePicker;

    @FXML
    private Label dateLabel;

    @FXML
    private Label selectedDate;

    @FXML
    void datePicker(ActionEvent event) {
        selectedDate.setText("Selected Date: " + datePicker.getValue().toString());
    }

    /**
     * Image
     */

    @FXML
    ImageView myImageView;
    Button myButton;

    Image myImage = new Image(getClass().getResourceAsStream("meme.jpg"));

    public void displayImage() {
        myImageView.setImage(myImage);
    }


}