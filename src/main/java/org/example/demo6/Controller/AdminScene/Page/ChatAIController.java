
package org.example.demo6.Controller.AdminScene.Page;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.demo6.Classes.ApiGoogleGemini;

public class ChatAIController {

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
}
