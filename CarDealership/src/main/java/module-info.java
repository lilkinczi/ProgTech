module com.example.cardealership {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cardealership to javafx.fxml;
    exports com.example.cardealership;
}