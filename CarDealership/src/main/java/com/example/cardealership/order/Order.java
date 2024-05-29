package com.example.cardealership.order;

import com.example.cardealership.car.Car;

public class Order {
    private int orderId;
    private int userId;
    private Car car;
    private int auto_id;
    private boolean napfenyteto;
    private boolean automataValto;
    private boolean zonasKlima;
    private boolean ulesfutes;
    private boolean tolatokamera;
    private String szin;
    private double teljesAr;

    public Order( int userId, Car car, boolean napfenyteto, boolean automataValto, boolean zonasKlima,
                 boolean ulesfutes, boolean tolatokamera, String szin, double teljesAr) {
        this.userId = userId;
        this.car = car;
        this.napfenyteto = napfenyteto;
        this.automataValto = automataValto;
        this.zonasKlima = zonasKlima;
        this.ulesfutes = ulesfutes;
        this.tolatokamera = tolatokamera;
        this.szin = szin;
        this.teljesAr = teljesAr;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public Car getCar() {
        return car;
    }

    public boolean isNapfenyteto() {
        return napfenyteto;
    }

    public boolean isAutomataValto() {
        return automataValto;
    }

    public boolean isZonasKlima() {
        return zonasKlima;
    }

    public boolean isUlesfutes() {
        return ulesfutes;
    }

    public boolean isTolatokamera() {
        return tolatokamera;
    }

    public String getSzin() {
        return szin;
    }

    public double getTeljesAr() {
        return teljesAr;
    }
}
