
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
    private TextArea userInput;

    private final ApiGoogleGemini apiGoogleGemini = new ApiGoogleGemini();

    // xu ly cau hoi
    @FXML
    public void handleSendMessage() {
        String userMessage = userInput.getText();

        if (userMessage.isEmpty()) {
            return;
        }

        chatDisplay.appendText("Bạn: " + userMessage + "\n");

        userInput.clear();
        userInput.setPromptText("Nhập câu hỏi của bạn");

        String aiResponse = apiGoogleGemini.sendPostRequest(userMessage);

        chatDisplay.appendText("AI: " + aiResponse + "\n" + "\n");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatDisplay.setWrapText(true);
        chatDisplay.setEditable(false);
        userInput.setWrapText(true);

        chatDisplay.appendText("Xin chào! Hôm nay tôi có thể giúp gì cho bạn?");
        chatDisplay.appendText("\n");
    }

    // enter de gui, shift + enter de xuong dong
    public void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.isShiftDown()) {
                userInput.appendText("\n");
            } else {
                handleSendMessage();
                event.consume();
            }
        }
    }
}
