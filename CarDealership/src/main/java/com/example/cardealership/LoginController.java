package com.example.cardealership;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private Stage dialogStage;
    private static String username;
    private boolean isAdmin = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
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

        if (isLoginValid(username, password)) {
            Main.setCurrentUser(username, isAdmin);
            dialogStage.close();
            Main.showMainView();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Hiba");
            alert.setHeaderText("Sikertelen bejelentkezés");
            alert.setContentText("Érvénytelen felhasználónév vagy jelszó.");
            alert.showAndWait();
        }
    }

    private boolean isLoginValid(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    isAdmin = rs.getBoolean("admin");
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
