package com.example.cardealership;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {
    @FXML
    private Menu carTypeMenu;

    @FXML
    private ListView<Car> carListView;

    @FXML
    private Label carDetailsLabel;

    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private CheckBox extra1CheckBox;

    @FXML
    private CheckBox extra2CheckBox;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private AnchorPane ordersPane;

    @FXML
    private TableView<Order> ordersTableView;

    @FXML
    private TableColumn<Order, String> carColumn;

    @FXML
    private TableColumn<Order, String> colorColumn;

    @FXML
    private TableColumn<Order, Boolean> extra1Column;

    @FXML
    private TableColumn<Order, Boolean> extra2Column;

    @FXML
    private TableColumn<Order, Double> priceColumn;

    private Car selectedCar;

    public void initialize() {
        for (CarType type : CarType.values()) {
            MenuItem menuItem = new MenuItem(type.name());
            menuItem.setOnAction(e -> showCarsByType(type));
            carTypeMenu.getItems().add(menuItem);
        }

        colorComboBox.setItems(FXCollections.observableArrayList("Red", "Blue", "Black", "White", "Silver"));
        colorComboBox.setOnAction(e -> updateTotalPrice());
        extra1CheckBox.setOnAction(e -> updateTotalPrice());
        extra2CheckBox.setOnAction(e -> updateTotalPrice());

        carListView.setOnMouseClicked(this::showCarDetails);

        // Set up the orders table columns
        carColumn.setCellValueFactory(new PropertyValueFactory<>("carName"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
        extra1Column.setCellValueFactory(new PropertyValueFactory<>("extra1"));
        extra2Column.setCellValueFactory(new PropertyValueFactory<>("extra2"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }

    private void showCarsByType(CarType type) {
        carListView.getItems().setAll(CarData.getCarsByType(type));
        carListView.setVisible(true);
        detailsPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    private void showCarDetails(MouseEvent event) {
        selectedCar = carListView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            carDetailsLabel.setText(
                    "Brand: " + selectedCar.getBrand() + "\n" +
                            "Model: " + selectedCar.getModel() + "\n" +
                            "Base Price: $" + selectedCar.getBasePrice()
            );
            updateTotalPrice();
            carListView.setVisible(false);
            detailsPane.setVisible(true);
            ordersPane.setVisible(false);
        }
    }

    @FXML
    private void handleBack() {
        carListView.setVisible(true);
        detailsPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    @FXML
    private void handleOrder() {
        Order order = new Order(
                selectedCar,
                colorComboBox.getValue(),
                extra1CheckBox.isSelected(),
                extra2CheckBox.isSelected(),
                Double.parseDouble(totalPriceLabel.getText().replace("Total Price: $", ""))
        );
        OrderData.saveOrder(order);
        handleBack();
    }

    @FXML
    private void showOrders() {
        ordersTableView.setItems(FXCollections.observableArrayList(OrderData.getOrders()));
        carListView.setVisible(false);
        detailsPane.setVisible(false);
        ordersPane.setVisible(true);
    }

    @FXML
    private void handleBackToCars() {
        carListView.setVisible(true);
        detailsPane.setVisible(false);
        ordersPane.setVisible(false);
    }

    private void updateTotalPrice() {
        if (selectedCar == null) return;

        double totalPrice = selectedCar.getBasePrice();
        if (colorComboBox.getValue() != null) {
            totalPrice += 500; // Assume each color adds a flat $500
        }
        if (extra1CheckBox.isSelected()) {
            totalPrice += 1000; // Example extra 1 adds $1000
        }
        if (extra2CheckBox.isSelected()) {
            totalPrice += 1500; // Example extra 2 adds $1500
        }

        totalPriceLabel.setText("Total Price: $" + totalPrice);
    }
}
