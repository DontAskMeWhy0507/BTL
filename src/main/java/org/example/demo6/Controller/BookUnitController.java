package org.example.demo6.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.demo6.Book;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookUnitController extends Node {

    @FXML
    private Label authorBook;

    @FXML
    private Button buttonFavorite;

    @FXML
    private ImageView heartImage;

    @FXML
    private ImageView imageBook;

    @FXML
    private Label nameBook;

    private boolean isFavorite = false;

    @FXML
    public void favorite(ActionEvent event) {
        if (isFavorite) {
            Image heart = new Image(getClass().getResourceAsStream("/Image/heartnone.png"));
            heartImage.setImage(heart);
            isFavorite = false;
        } else {
            Image heart = new Image(getClass().getResourceAsStream("/Image/heart.png"));
            heartImage.setImage(heart);
            isFavorite = true;
        }
    }

    public void setData(Book book) {
        nameBook.setText(book.getTitle());
        authorBook.setText(book.getAuthor());
        if (book.getCoverImagePath() != null) {
            imageBook.setImage(new Image(book.getCoverImagePath()));
        } else {
            imageBook.setImage(new Image(getClass().getResourceAsStream("/Image/heart.png")));
        }
    }

//    public void fetchBookDetails(String query) {
//        try {
//            String apiKey = "AIzaSyCyibUKHM6uRFhx6bzrhHyNDbt1GO3h-Lk";
//            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey;
//            URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String inputLine;
//            StringBuilder content = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//            conn.disconnect();
//
//            JSONObject json = new JSONObject(content.toString());
//            JSONArray items = json.getJSONArray("items");
//            if (items.length() > 0) {
//                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
//                String title = volumeInfo.getString("title");
//                String author = volumeInfo.getJSONArray("authors").getString(0);
//                String imageUrl = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");
//
//                Book book = new Book(null, title, author, null, null, null, null, null, imageUrl, null);
//                setData(book);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void switchToBookDetails(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/BookPreview.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}