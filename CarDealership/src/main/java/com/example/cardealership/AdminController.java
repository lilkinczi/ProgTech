package com.example.cardealership;

import com.example.cardealership.car.Car;
import com.example.cardealership.car.ConcreteCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class AdminController {

    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableColumn<Car, String> colGyarto;
    @FXML
    private TableColumn<Car, String> colNev;
    @FXML
    private TableColumn<Car, String> colTipus;
    @FXML
    private TableColumn<Car, Integer> colAr;

    private ObservableList<Car> carList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colGyarto.setCellValueFactory(new PropertyValueFactory<>("gyarto"));
        colNev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        colTipus.setCellValueFactory(new PropertyValueFactory<>("tipus"));
        colAr.setCellValueFactory(new PropertyValueFactory<>("ar"));

        loadCarData();
    }

    private void loadCarData() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "password");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM auto")) {

            while (rs.next()) {
                carList.add(new ConcreteCar(rs.getString("gyarto"), rs.getString("nev"), rs.getString("tipus"), rs.getInt("ar")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        carTableView.setItems(carList);
    }

    @FXML
    private void handleAddCar() {
        showCarEditorDialog(null);
    }

    @FXML
    public void handleEditCar() {
        Car selectedCar = carTableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            showCarEditorDialog(selectedCar);
        }
    }

    @FXML
    private void handleDeleteCar() {
        Car selectedCar = carTableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "password");
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM auto WHERE gyarto='" + selectedCar.getBrand() + "' AND nev='" + selectedCar.getModel() + "'");
                carList.remove(selectedCar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showCarEditorDialog(Car car) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("carEditor.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(car == null ? "Autó hozzáadása" : "Autó szerkesztése");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(carTableView.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CarEditorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCar(car);
            controller.setAdminController(this);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCarToDatabase(Car car) {
        carList.add(car);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "password");
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO auto (gyarto, nev, tipus, ar) VALUES (?, ?, ?, ?)")) {
            pstmt.setString(1, car.getBrand());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getType());
            pstmt.setDouble(4, car.getPrice());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCarInDatabase(Car car) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "password");
             PreparedStatement pstmt = conn.prepareStatement("UPDATE auto SET gyarto = ?, nev = ?, tipus = ?, ar = ? WHERE gyarto = ? AND nev = ?")) {
            pstmt.setString(1, car.getBrand());
            pstmt.setString(2, car.getModel());
            pstmt.setString(3, car.getType());
            pstmt.setDouble(4, car.getPrice());
            pstmt.setString(5, car.getBrand());
            pstmt.setString(6, car.getModel());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
