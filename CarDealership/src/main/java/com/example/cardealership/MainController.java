package com.example.cardealership;

import com.example.cardealership.car.Car;
import com.example.cardealership.car.ColorDecorator;
import com.example.cardealership.car.ExtraDecorator;
import com.example.cardealership.order.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.PreparedStatement;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainController {
    @FXML
    private ListView<Car> carListView;
    @FXML
    private ComboBox<String> colorComboBox;
    @FXML
    private CheckBox extra1CheckBox;
    @FXML
    private CheckBox extra2CheckBox;
    @FXML
    private CheckBox extra3CheckBox;
    @FXML
    private CheckBox extra4CheckBox;
    @FXML
    private CheckBox extra5CheckBox;
    @FXML
    private Label carDetailsLabel;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private AnchorPane detailsPane;
    @FXML
    private AnchorPane ordersPane;
    @FXML
    private TableView<Order> ordersTableView;
    @FXML
    private TableColumn<Order, String> carBrandColumn;
    @FXML
    private TableColumn<Order, String> carModelColumn;
    @FXML
    private TableColumn<Order, String> colorColumn;
    @FXML
    private TableColumn<Order, Boolean> extra1Column;
    @FXML
    private TableColumn<Order, Boolean> extra2Column;
    @FXML
    private TableColumn<Order, Boolean> extra3Column;
    @FXML
    private TableColumn<Order, Boolean> extra4Column;
    @FXML
    private TableColumn<Order, Boolean> extra5Column;
    @FXML
    private TableColumn<Order, Double> totalPriceColumn;
    @FXML
    private Menu carTypeMenu;
    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    private boolean isAdmin;

    private Car selectedCar;
    private ObservableList<Car> carList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        loadCarTypes();

        colorComboBox.getItems().addAll("Red", "Blue", "Green", "Black", "White");

        extra1CheckBox.setOnAction(e -> updateTotalPrice());
        extra2CheckBox.setOnAction(e -> updateTotalPrice());
        extra3CheckBox.setOnAction(e -> updateTotalPrice());
        extra4CheckBox.setOnAction(e -> updateTotalPrice());
        extra5CheckBox.setOnAction(e -> updateTotalPrice());

        carListView.setOnMouseClicked(this::showCarDetails);

        carBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        carModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        extra1Column.setCellValueFactory(new PropertyValueFactory<>("napfenyteto"));
        extra2Column.setCellValueFactory(new PropertyValueFactory<>("automataValto"));
        extra3Column.setCellValueFactory(new PropertyValueFactory<>("zonasKlima"));
        extra4Column.setCellValueFactory(new PropertyValueFactory<>("ulesfutes"));
        extra5Column.setCellValueFactory(new PropertyValueFactory<>("tolatokamera"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("teljesAr"));
    }
    public void setUser(String username, boolean isAdmin) {
        this.isAdmin = isAdmin;
        toggleAdminControls(isAdmin);
    }

    private void toggleAdminControls(boolean isAdmin) {
        addButton.setVisible(isAdmin);
        deleteButton.setVisible(isAdmin);
        editButton.setVisible(isAdmin);
    }

    private void loadCarTypes() {
        List<String> carTypes = DatabaseConnection.getCarTypes();
        for (String type : carTypes) {
            MenuItem item = new MenuItem(type);
            item.setOnAction(e -> showCarsByType(type));
            carTypeMenu.getItems().add(item);
        }
    }

    private void showCars() {
        carListView.getItems().addAll(DatabaseConnection.getCars());
        carListView.setVisible(true);
        detailsPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    private void showCarsByType(String type) {
        carListView.getItems().setAll(DatabaseConnection.getCarsByType(type));
        carListView.setVisible(true);
        detailsPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    private void showCarDetails(MouseEvent event) {
        selectedCar = carListView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            carDetailsLabel.setText(selectedCar.getDescription() + "\nBase Price: " + selectedCar.getPrice());
            updateTotalPrice();
            carListView.setVisible(false);
            detailsPane.setVisible(true);
        }
    }

    private void updateTotalPrice() {
        if (selectedCar != null) {
            Car carWithColor = new ColorDecorator(selectedCar, colorComboBox.getValue());
            Car carWithExtras = carWithColor;
            if (extra1CheckBox.isSelected()) {
                carWithExtras = new ExtraDecorator(carWithExtras, "Napfenyteto", 100000);
            }
            if (extra2CheckBox.isSelected()) {
                carWithExtras = new ExtraDecorator(carWithExtras, "Automata Valto", 200000);
            }
            if (extra3CheckBox.isSelected()) {
                carWithExtras = new ExtraDecorator(carWithExtras, "2 Zonas Klima", 50000);
            }
            if (extra4CheckBox.isSelected()) {
                carWithExtras = new ExtraDecorator(carWithExtras, "Ulesfutes", 20000);
            }
            if (extra5CheckBox.isSelected()) {
                carWithExtras = new ExtraDecorator(carWithExtras, "Tolatokamera", 30000);
            }
            totalPriceLabel.setText("Total Price: " + carWithExtras.getPrice());
        }
    }

    @FXML
    private void placeOrder() {
        String username = LoginController.getUsername();
        if (selectedCar != null) {
            String color = colorComboBox.getValue();
            boolean extra1 = extra1CheckBox.isSelected();
            boolean extra2 = extra2CheckBox.isSelected();
            boolean extra3 = extra3CheckBox.isSelected();
            boolean extra4 = extra4CheckBox.isSelected();
            boolean extra5 = extra5CheckBox.isSelected();
            double totalPrice = new ExtraDecorator(new ExtraDecorator(new ExtraDecorator(new ExtraDecorator(new ExtraDecorator(new ColorDecorator(selectedCar, color), "Napfenyteto", extra1 ? 1500 : 0), "Automata Valto", extra2 ? 2000 : 0), "2 Zonas Klima", extra3 ? 1200 : 0), "Ulesfutes", extra4 ? 1000 : 0), "Tolatokamera", extra5 ? 800 : 0).getPrice();
            Order order = new Order(DatabaseConnection.getUserID(username), selectedCar, extra1, extra2, extra3, extra4, extra5, color, totalPrice); // 1 as user ID should be dynamic based on logged-in user
            DatabaseConnection.saveOrder(order);
            showOrders();
        }
    }

    @FXML
    private void showOrders() {
        List<Order> orders = DatabaseConnection.getOrders();
        ordersTableView.setItems(FXCollections.observableArrayList(orders));
        carListView.setVisible(false);
        detailsPane.setVisible(false);
        ordersPane.setVisible(true);
    }

    @FXML
    private void handleAddCar() {
        showCarEditorDialog(null);
    }

    @FXML
    private void handleEditCar() {
        Car selectedCar = carListView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            showCarEditorDialog(selectedCar);
        }
    }

    @FXML
    private void handleDeleteCar() {
        Car selectedCar = carListView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM auto WHERE gyarto=? AND nev=?")) {
                pstmt.setString(1, selectedCar.getBrand());
                pstmt.setString(2, selectedCar.getModel());
                pstmt.executeUpdate();
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
            dialogStage.initOwner(carListView.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CarEditorController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCar(car);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCarToDatabase(Car car) {
        carList.add(car);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
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
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
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
    @FXML
    private void backToList() {
        showCars();
    }


}
