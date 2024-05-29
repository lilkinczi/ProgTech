module com.example.cardealership {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cardealership to javafx.fxml;
    exports com.example.cardealership;
    exports com.example.cardealership.car;
    opens com.example.cardealership.car to javafx.fxml;
    exports com.example.cardealership.order;
    opens com.example.cardealership.order to javafx.fxml;
}