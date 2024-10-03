//& 'C:\Program Files\Java\jdk-23\bin\java.exe' '@C:\Users\tuant\AppData\Local\Temp\cp_5bzpu7zzr2s2oo09gkzqucn9b.argfile' 'App'
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Uncomment the following lines if you want to use FXML
        // Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        // Scene scene = new Scene(root);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}