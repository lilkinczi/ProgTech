package com.example.cardealership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarData {
    private static ObservableList<Car> cars = FXCollections.observableArrayList();

    public static ObservableList<Car> getCars() {
        return cars;
    }

    public static void loadCars(CarType type) {
        cars.setAll(DatabaseConnection.getCarsByType(type));
    }
}
