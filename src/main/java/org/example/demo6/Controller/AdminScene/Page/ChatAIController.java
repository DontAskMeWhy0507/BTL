
package org.example.demo6.Controller.AdminScene.Page;

import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.demo6.Classes.ApiGoogleGemini;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.beans.binding.Bindings.createDoubleBinding;

public class ChatAIController implements Initializable {
    @FXML
    private TextArea chatDisplay;

    @FXML
    private TextField userInput;

    private final ApiGoogleGemini apiGoogleGemini = new ApiGoogleGemini();

    @FXML
    public void handleSendMessage() {
        // Get user input
        String userMessage = userInput.getText();

        if (userMessage.isEmpty()) {
            return;
        }

        // Append user message to chat display
        chatDisplay.appendText("You: " + userMessage + "\n");

        // Clear the input field
        userInput.clear();

        // Send the message to the API and get the response
        String aiResponse = apiGoogleGemini.sendPostRequest(userMessage);

        // Append AI response to chat display
        chatDisplay.appendText("AI: " + aiResponse + "\n");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            // Calculate width based on text length
            int textLength = newValue.length();
            double newWidth = Math.max(50, textLength * 10); // Ensure minimum width of 50
            userInput.setPrefWidth(newWidth); // Set the new preferred width
        });
    }

    public void Enter(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            handleSendMessage();
        }
    }


}
