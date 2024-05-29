package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private Button registerButton;

    @FXML
    public void initialize() {
        registerButton.setOnMouseClicked(event -> handleRegister(event));
    }

    private void handleRegister(MouseEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        if (DatabaseConnection.registerUser(username, password, email, address)) {
            try {
                Stage stage = (Stage) registerButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Registration failed!");
        }
    }
}

