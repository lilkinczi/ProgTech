package com.example.cardealership.car;

public class ConcreteCarFactory implements CarFactory {
    @Override
    public Car createCar(int id, String brand, String model,String tipus, double basePrice) {
        return new ConcreteCar(id, brand, model,tipus, basePrice);
    }
}
