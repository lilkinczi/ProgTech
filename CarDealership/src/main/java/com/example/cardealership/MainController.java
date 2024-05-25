package com.example.cardealership;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

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
    }

    private void showCarsByType(CarType type) {
        carListView.getItems().setAll(CarData.getCarsByType(type));
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
        }
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
