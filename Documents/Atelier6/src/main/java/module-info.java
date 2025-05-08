module org.example.atelier6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.atelier6 to javafx.fxml;
    exports org.example.atelier6;
}