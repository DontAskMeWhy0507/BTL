module org.example.demo6 {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires com.jfoenix;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.services.books;
    requires transitive org.xerial.sqlitejdbc;

    opens org.example.demo6 to javafx.fxml;
    exports org.example.demo6;
    exports org.example.demo6.Classes;
    opens org.example.demo6.Classes to javafx.fxml;
    exports org.example.demo6.Controller.AdminScene;
    opens org.example.demo6.Controller.AdminScene to javafx.fxml;
    exports org.example.demo6.Controller.AdminScene.Page;
    opens org.example.demo6.Controller.AdminScene.Page to javafx.fxml;
    exports org.example.demo6.Controller.LoginScene;
    opens org.example.demo6.Controller.LoginScene to javafx.fxml;
    exports org.example.demo6.Controller.UserScene;
    opens org.example.demo6.Controller.UserScene to javafx.fxml;
    exports org.example.demo6.Controller.UserScene.Page;
    opens org.example.demo6.Controller.UserScene.Page to javafx.fxml;

    requires javafx.controls;
    requires json;
    requires java.net.http;
    requires javafx.media;
    requires jdk.compiler;
    requires com.google.errorprone.annotations;


}