package com.example.cardealership.car;

public class ExtraDecorator extends CarDecorator {
    private String extra;
    private double extraPrice;

    public ExtraDecorator(Car decoratedCar, String extra, double extraPrice) {
        super(decoratedCar);
        this.extra = extra;
        this.extraPrice = extraPrice;
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
        return decoratedCar.getPrice() + extraPrice;
    }

    @Override
    public String getDescription() {
        return decoratedCar.getDescription() + " (" + extra + ")";
    }
}
