package com.example.cardealership;

import com.example.cardealership.car.Car;
import com.example.cardealership.car.ConcreteCar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarEditorController {

    @FXML
    private TextField gyartoField;
    @FXML
    private TextField nevField;
    @FXML
    private TextField tipusField;
    @FXML
    private TextField arField;
    @FXML
    private Label errorLabel;

    private Car car;
    private AdminController adminController;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCar(Car car) {
        this.car = car;
        if (car != null) {
            gyartoField.setText(car.getBrand());
            nevField.setText(car.getModel());
            tipusField.setText(car.getType());
            arField.setText(String.valueOf(car.getPrice()));
        }
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    @FXML
    private void handleSave(ActionEvent event) {
        String gyarto = gyartoField.getText();
        String nev = nevField.getText();
        String tipus = tipusField.getText();
        int ar;

        try {
            ar = Integer.parseInt(arField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Az árnak egy számnak kell lennie.");
            return;
        }

        if (gyarto.isEmpty() || nev.isEmpty() || tipus.isEmpty()) {
            errorLabel.setText("Minden mezőt ki kell tölteni.");
            return;
        }

        if (car == null) {
            car = new ConcreteCar(gyarto, nev, tipus, ar);
            adminController.addCarToDatabase(car);
        }

        dialogStage.close();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }
}
