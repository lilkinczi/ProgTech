package com.example.cardealership.car;

public interface CarFactory {
    Car createCar(int id, String brand, String model,String tipus, double basePrice);
}
