module org.example.atelier7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.atelier7.controllers to javafx.fxml;
    opens org.example.atelier7 to javafx.fxml;
    exports org.example.atelier7;

}