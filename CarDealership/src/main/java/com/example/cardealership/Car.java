package com.example.cardealership;

public class Car {
    private String brand;
    private String model;
    private CarType type;
    private double basePrice;

    public Car(String brand, String model, CarType type, double basePrice) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.basePrice = basePrice;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public CarType getType() {
        return type;
    }

    public double getBasePrice() {
        return basePrice;
    }

    @Override
    public String toString() {
        return brand + " " + model;
    }
}
