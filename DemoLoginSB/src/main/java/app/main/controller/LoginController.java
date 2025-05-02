package app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.main.config.SceneSwitcher;
import app.main.dto.UserLoginDto;
import app.main.service.UserService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

@Component
public class LoginController {

    @Autowired
    private UserService userService;

    @FXML private Label lblLoginTitle;
    @FXML private Label lblLoginUserName;
    @FXML private VBox VBoxLoginUserName;
    @FXML private TextField txtLoginUserName;
    @FXML private Label lblLoginUserNameResult;
    @FXML private Label lblLoginPassword;
    @FXML private VBox VBoxLoginPassword;
    @FXML private PasswordField pwdLoginPassword;
    @FXML private Label lblLoginPasswordStrengthResult;
    @FXML private Label lblLoginResult;
    @FXML private Button btnLogin;
    @FXML private Button btnLoginRegister;
    @FXML private AnchorPane anchPaneLoginMain;

    @FXML
    public void initialize() {
    }

    @FXML
    private void onRegisterLinkClicked(Event event) {
        try {
            SceneSwitcher.switchSceneWithFade(anchPaneLoginMain, "/fxml/Register.fxml", "Regisztráció");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogin() {
        clearErrors();

        String userName = txtLoginUserName.getText().trim();
        String password = pwdLoginPassword.getText().trim();
        boolean valid = true;

        if (userName.isEmpty()) {
            showUserNameError("Felhasználónév megadása kötelező!");
            valid = false;
        } else if (!userService.usernameExists(userName)) {
            showUserNameError("Nincs ilyen felhasználó!");
            valid = false;
        }

        if (password.isEmpty()) {
            showPasswordError("Jelszó megadása kötelező!");
            valid = false;
        }

        if (!valid) return;

        UserLoginDto dto = new UserLoginDto();
        dto.setUsername(userName);
        dto.setPassword(password);

        boolean loginSuccess = userService.login(dto);

        if (loginSuccess) {
            lblLoginResult.setText("Sikeres bejelentkezés!");
            lblLoginResult.setStyle("-fx-text-fill: green;");
        } else {
            showPasswordError("Hibás jelszó!");
            lblLoginResult.setStyle("-fx-text-fill: red;");
            lblLoginResult.setText("");
        }
    }

    private void showUserNameError(String msg) {
        lblLoginUserNameResult.setText(msg);
        if (!lblLoginUserNameResult.getStyleClass().contains("error-label")) {
            lblLoginUserNameResult.getStyleClass().add("error-label");
        }
        if (!txtLoginUserName.getStyleClass().contains("error")) {
            txtLoginUserName.getStyleClass().add("error");
        }
    }

    private void showPasswordError(String msg) {
        lblLoginPasswordStrengthResult.setText(msg);
        if (!lblLoginPasswordStrengthResult.getStyleClass().contains("error-label")) {
            lblLoginPasswordStrengthResult.getStyleClass().add("error-label");
        }
        if (!pwdLoginPassword.getStyleClass().contains("error")) {
            pwdLoginPassword.getStyleClass().add("error");
        }
    }

    private void clearErrors() {
        txtLoginUserName.getStyleClass().removeAll("error");
        pwdLoginPassword.getStyleClass().removeAll("error");

        lblLoginUserNameResult.setText("");
        lblLoginUserNameResult.getStyleClass().removeAll("error-label");

        lblLoginPasswordStrengthResult.setText("");
        lblLoginPasswordStrengthResult.getStyleClass().removeAll("error-label");

        lblLoginResult.setText("");
    }
}

