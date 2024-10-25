module org.example.demo6 {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.sql;
    requires com.jfoenix;
    requires com.google.api.client;
    requires com.google.api.client.json.jackson2;
    requires com.google.api.services.books;

    opens org.example.demo6 to javafx.fxml;
    exports org.example.demo6;
    exports org.example.demo6.Admin;
    opens org.example.demo6.Admin to javafx.fxml;
    exports org.example.demo6.Controller;
    opens org.example.demo6.Controller to javafx.fxml;

    requires javafx.controls;
    requires json;


}