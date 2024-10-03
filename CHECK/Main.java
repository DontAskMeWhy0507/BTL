import javax.crypto.Cipher;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        // Scene scene = new Scene(root);
        // primaryStage.setScene(scene);
        // primaryStage.setTitle("Hello World");
        // primaryStage.show();
        Group root = new Group();
        Scene scene = new Scene(root, 600,  600, Color.LIGHTSKYBLUE);
        final Stage stage = new Stage();
        Text text  = new Text();
        text.setText("DuMaMay Noi It thoi chu");
        text.setX(50);
        text.setY((50));
        text.setFont(Font.font("Times New Roman", 50));
        text.setFill(Color.LIMEGREEN);

        Line line  = new Line();
        line.setStartX(200);
        line.setStartY(200);
        line.setEndX(500);
        line.setEndY(200);
        line.setStrokeWidth(5);
        line.setStroke(Color.BEIGE);
         line.setOpacity(0.5);
        line.setRotate(45);
        
        Rectangle rectangle =  new Rectangle();
        rectangle.setX(100);
        rectangle.setY(100);
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        rectangle.setFill(Color.BLUE);
        rectangle.setStrokeWidth(5);
        rectangle.setStroke(Color.BLACK);

        Polygon triangle  = new Polygon();
        triangle.getPoints().setAll(
            200.0,200.0,
            300.0,300.0,
            200.0,300.0);
        triangle.setFill(Color.YELLOW);

        Circle circle = new Circle();
        circle.setCenterX(350);
        circle.setCenterY(350);
        circle.setRadius(50);
        circle.setFill(Color.ORANGE);

        Image image = new Image("meme.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setX(400);
        imageView.setY(400);

        root.getChildren().add(text);
        root.getChildren().add(line);
        root.getChildren().add(rectangle);
        root.getChildren().add(triangle);
        root.getChildren().add(circle);
        root.getChildren().add(imageView);
        // Image icon = new Image("j1.png");
        // stage.getIcons().add(icon);
        // stage.setTitle("DUMAMAY NOI IT THOI");

        // stage.setWidth(1000);
        // stage.setHeight(500);
        // // stage.setX(50);
        // // stage.setY(50);
        // stage.setFullScreen(true);
        // stage.setFullScreenExitHint("YOU CANT ESCAPE unless you press q");
        // stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        // stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }  
}
