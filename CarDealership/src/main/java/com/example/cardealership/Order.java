package com.example.cardealership;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private Car car;
    private SimpleStringProperty color;
    private SimpleBooleanProperty extra1;
    private SimpleBooleanProperty extra2;
    private SimpleDoubleProperty totalPrice;

    public Order(Car car, String color, boolean extra1, boolean extra2, double totalPrice) {
        this.car = car;
        this.color = new SimpleStringProperty(color);
        this.extra1 = new SimpleBooleanProperty(extra1);
        this.extra2 = new SimpleBooleanProperty(extra2);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
    }

    public Car getCar() {
        return car;
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public boolean isExtra1() {
        return extra1.get();
    }

    public SimpleBooleanProperty extra1Property() {
        return extra1;
    }

    public boolean isExtra2() {
        return extra2.get();
    }

    public SimpleBooleanProperty extra2Property() {
        return extra2;
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }
}
