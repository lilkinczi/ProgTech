package com.example.cardealership;

import com.example.cardealership.car.Car;
import com.example.cardealership.car.ConcreteCar;
import com.example.cardealership.car.ConcreteCarFactory;
import com.example.cardealership.car.CarFactory;
import com.example.cardealership.order.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/progtech";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static CarFactory carFactory = new ConcreteCarFactory();

    private static String currentuser;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validateUser(String username, String password) {
        currentuser = username;
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isAdmin(String username) {
        String sql = "SELECT admin FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("admin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerUser(String username, String password, String email, String address) {
        String sql = "INSERT INTO users (username, password, email, szallitasi_cim, admin) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM auto";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(carFactory.createCar(rs.getInt("auto_id"), rs.getString("gyarto"), rs.getString("nev"), rs.getString("tipus"), rs.getDouble("ar")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static List<Car> getCarsByType(String type) {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM auto WHERE tipus = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new ConcreteCar(
                        rs.getInt("auto_id"),
                        rs.getString("gyarto"),
                        rs.getString("nev"),
                        rs.getString("tipus"),
                        rs.getInt("ar")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cars;
    }
    public static int getUserID(String name) {
        int id = 0;
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

             stmt.setString(1, name);
             ResultSet rs = stmt.executeQuery();

             while (rs.next()){
                 return rs.getInt("id");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public static List<String> getCarTypes() {
        List<String> types = new ArrayList<>();
        String sql = "SELECT DISTINCT tipus FROM auto";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                types.add(rs.getString("tipus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return types;
    }

    public static void saveOrder(Order order) {
        String sql = "INSERT INTO orders (id, auto_id, gyarto, nev, napfenyteto, automata_valto, 2zonas_klima, ulesfutes, tolatokamera, szin, teljes_ar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getUserId());
            stmt.setInt(2, order.getAutoId());
            stmt.setString(3, order.getGyarto());
            stmt.setString(4, order.getNev());
            stmt.setBoolean(5, order.isNapfenyteto());
            stmt.setBoolean(6, order.isAutomataValto());
            stmt.setBoolean(7, order.isZonasKlima());
            stmt.setBoolean(8, order.isUlesfutes());
            stmt.setBoolean(9, order.isTolatokamera());
            stmt.setString(10, order.getSzin());
            stmt.setDouble(11, order.getTeljesAr());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new ConcreteCar(rs.getInt("auto_id"), rs.getString("gyarto"), rs.getString("nev"), rs.getString("tipus"), rs.getDouble("ar"));
                Order order = new Order( rs.getInt("id"), car.getAuto_Id(), rs.getString("gyarto"), rs.getString("nev"), rs.getBoolean("napfenyteto"), rs.getBoolean("automata_valto"),
                        rs.getBoolean("2zonas_klima"), rs.getBoolean("ulesfutes"), rs.getBoolean("tolatokamera"),rs.getString("szin"), rs.getDouble("teljes_ar"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static List<Order> getUserOrders(String username) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, c.gyarto, c.nev, c.tipus, c.ar " +
                "FROM orders o " +
                "JOIN auto c ON o.auto_id = c.auto_id " +
                "JOIN users u ON o.id = u.id " +
                "WHERE u.username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = new ConcreteCar(rs.getInt("auto_id"), rs.getString("gyarto"), rs.getString("nev"), rs.getString("tipus"), rs.getDouble("ar"));
                Order order = new Order(rs.getInt("id"), car.getAuto_Id(), rs.getString("gyarto"), rs.getString("nev"), rs.getBoolean("napfenyteto"), rs.getBoolean("automata_valto"),
                        rs.getBoolean("2zonas_klima"), rs.getBoolean("ulesfutes"), rs.getBoolean("tolatokamera"),rs.getString("szin"), rs.getDouble("teljes_ar"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
