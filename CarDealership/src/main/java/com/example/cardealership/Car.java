package com.example.cardealership;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Car {
    private SimpleIntegerProperty id;
    private SimpleStringProperty brand;
    private SimpleStringProperty model;
    private SimpleDoubleProperty basePrice;

    public Car(int id, String brand, String model, double basePrice) {
        this.id = new SimpleIntegerProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.basePrice = new SimpleDoubleProperty(basePrice);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public double getBasePrice() {
        return basePrice.get();
    }

    public SimpleDoubleProperty basePriceProperty() {
        return basePrice;
    }
}
