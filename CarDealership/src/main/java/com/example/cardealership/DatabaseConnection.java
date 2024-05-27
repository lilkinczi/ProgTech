package com.example.cardealership;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/progtech";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Car> getCarsByType(CarType type) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE brand = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new Car(rs.getInt("auto_id"), rs.getString("brand"), rs.getString("model"), rs.getDouble("base_price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static void saveOrder(Order order) {
        String sql = "INSERT INTO orders (car_id, color, extra1, extra2, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCar().getId());
            stmt.setString(2, order.getColor());
            stmt.setBoolean(3, order.isExtra1());
            stmt.setBoolean(4, order.isExtra2());
            stmt.setDouble(5, order.getTotalPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, c.brand, c.model FROM orders o JOIN cars c ON o.car_id = c.id";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new Car(rs.getInt("car_id"), rs.getString("brand"), rs.getString("model"), rs.getDouble("base_price"));
                Order order = new Order(car, rs.getString("color"), rs.getBoolean("extra1"), rs.getBoolean("extra2"), rs.getDouble("total_price"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
