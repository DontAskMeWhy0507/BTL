module org.example.demo6 {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.sql;
    requires com.jfoenix;

    opens org.example.demo6 to javafx.fxml;
    exports org.example.demo6;
}