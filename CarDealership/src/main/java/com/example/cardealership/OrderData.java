package com.example.cardealership;

import java.util.ArrayList;
import java.util.List;

public class OrderData {
    private static List<Order> orders = new ArrayList<>();

    public static void saveOrder(Order order) {
        orders.add(order);
        System.out.println("Order saved: " + order);
    }

    public static List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
