package org.example.demo6;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    private Button buttonlogOut;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonlogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUltis.changescene(event, "/View/Login.fxml", "Login!");
            }
        });
    }
}
