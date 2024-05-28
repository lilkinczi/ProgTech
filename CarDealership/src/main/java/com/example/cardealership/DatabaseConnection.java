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

    public static List<Car> getCarsByType(String type) { // Assuming CarType is no longer used
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM auto WHERE tipus = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new Car(rs.getInt("auto_id"), rs.getString("gyarto"), rs.getString("nev"), rs.getDouble("ar")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static void saveOrder(Order order) {
        String sql = "INSERT INTO oreders (auto_id, szin, napfenyteto, automata_valto, 2zonas_klima, ulesfutes, tolatokamera, teljes_ar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCar().getId());
            stmt.setString(2, order.getColor());
            stmt.setBoolean(3, order.isSunroof());
            stmt.setBoolean(4, order.isAutomaticTransmission());
            stmt.setBoolean(5, order.isDualZoneClimateControl());
            stmt.setBoolean(6, order.isSeatHeating());
            stmt.setBoolean(7, order.isBackupCamera());
            stmt.setDouble(8, order.getTotalPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, a.gyarto, a.nev FROM oreders o JOIN auto a ON o.auto_id = a.auto_id";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new Car(rs.getInt("auto_id"), rs.getString("gyarto"), rs.getString("nev"), rs.getDouble("ar"));
                Order order = new Order(car, rs.getString("szin"), rs.getBoolean("napfenyteto"), rs.getBoolean("automata_valto"), rs.getBoolean("2zonas_klima"), rs.getBoolean("ulesfutes"), rs.getBoolean("tolatokamera"), rs.getDouble("teljes_ar"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
