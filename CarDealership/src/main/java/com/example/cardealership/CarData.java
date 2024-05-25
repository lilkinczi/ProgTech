package com.example.cardealership;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarData {
    private static final List<Car> cars = new ArrayList<>();

    static {
        cars.add(new Car("Toyota", "Camry", CarType.SEDAN, 25000));
        cars.add(new Car("Ford", "Explorer", CarType.SUV, 35000));
        cars.add(new Car("Chevrolet", "Silverado", CarType.TRUCK, 40000));
        cars.add(new Car("Honda", "Civic", CarType.SEDAN, 22000));
        cars.add(new Car("Mazda", "MX-5", CarType.CONVERTIBLE, 30000));
        cars.add(new Car("Ford", "Mustang", CarType.COUPE, 45000));
    }

    public static List<Car> getCarsByType(CarType type) {
        return cars.stream()
                .filter(car -> car.getType() == type)
                .collect(Collectors.toList());
    }
}
