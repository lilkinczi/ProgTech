package com.example.cardealership.car;

public class ConcreteCar implements Car {
    private int id;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String brand;
    private String model;
    private String type;
    private double price;

    public ConcreteCar(int id, String brand, String model, String type, double price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.price = price;
    }
    public ConcreteCar(String brand, String model, String type, double price) {
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.price = price;
    }

    @Override
    public int getAuto_Id() {
        return id;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return brand + " " + model;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
