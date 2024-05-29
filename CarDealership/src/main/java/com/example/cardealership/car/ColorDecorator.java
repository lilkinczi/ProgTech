package com.example.cardealership.car;

public class ColorDecorator extends CarDecorator {
    private String color;

    public ColorDecorator(Car decoratedCar, String color) {
        super(decoratedCar);
        this.color = color;
    }

    @Override
    public int getAuto_Id() {
        return decoratedCar.getAuto_Id();
    }

    @Override
    public String getBrand() {
        return decoratedCar.getBrand();
    }

    @Override
    public String getModel() {
        return decoratedCar.getModel();
    }

    @Override
    public String getType() {
        return decoratedCar.getType();
    }

    @Override
    public double getPrice() {
        return decoratedCar.getPrice();
    }

    @Override
    public String getDescription() {
        return decoratedCar.getDescription() + " (Color: " + color + ")";
    }
}
