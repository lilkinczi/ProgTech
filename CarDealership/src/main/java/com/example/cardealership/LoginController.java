package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private static String username;

    @FXML
    public void initialize() {
        loginButton.setOnMouseClicked(event -> handleLogin());
        registerButton.setOnMouseClicked(event -> handleRegisterButtonClick());
    }

    public static String getUsername() {
        return username;
    }

    private void handleLogin() {
        username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseConnection.validateUser(username, password)) {
            try {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = loader.load();

                MainController mainController = loader.getController();
                mainController.setUser(username, DatabaseConnection.isAdmin(username));

                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Helytelen felhasználónév vagy jelszó!");
        }
    }

    private void handleRegisterButtonClick() {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
