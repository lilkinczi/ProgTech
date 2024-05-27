package com.example.cardealership;

public class Order {
    private Car car;
    private String color;
    private boolean extra1;
    private boolean extra2;
    private double totalPrice;

    public Order(Car car, String color, boolean extra1, boolean extra2, double totalPrice) {
        this.car = car;
        this.color = color;
        this.extra1 = extra1;
        this.extra2 = extra2;
        this.totalPrice = totalPrice;
    }

    public String getCarName() {
        return car.getBrand() + " " + car.getModel();
    }

    public String getColor() {
        return color;
    }

    public boolean isExtra1() {
        return extra1;
    }

    public boolean isExtra2() {
        return extra2;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Car: " + getCarName() +
                ", Color: " + color +
                ", Extra 1: " + (extra1 ? "Yes" : "No") +
                ", Extra 2: " + (extra2 ? "Yes" : "No") +
                ", Total Price: $" + totalPrice;
    }
}
