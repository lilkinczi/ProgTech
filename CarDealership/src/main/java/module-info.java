module com.example.cardealership {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cardealership to javafx.fxml;
    exports com.example.cardealership;
}