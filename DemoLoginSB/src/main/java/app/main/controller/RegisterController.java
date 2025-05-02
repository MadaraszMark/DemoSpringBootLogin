package app.main.controller;

import app.main.config.SceneSwitcher;
import app.main.dto.UserRegisterDto;
import app.main.service.UserService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterController {

    @FXML private AnchorPane anchPaneRegisterMain;
    @FXML private VBox VBoxMain;
    @FXML private Label lblRegisterTitle;
    @FXML private Label lblRegisterUserName;
    @FXML private VBox VBoxRegisterUserName;
    @FXML private TextField txtRegisterUserName;
    @FXML private Label lblRegisterUserNameResult;
    @FXML private Label lblRegisterEmail;
    @FXML private VBox VBoxRegisterEmail;
    @FXML private TextField txtRegisterEmail;
    @FXML private Label lblRegisterEmailResult;
    @FXML private Label lblRegisterPassword;
    @FXML private VBox VBoxLoginPassword;
    @FXML private PasswordField pwdLoginPassword;
    @FXML private Label lblLoginPasswordStrengthResult;
    @FXML private Label lblRegisterResult;
    @FXML private Button btnRegister;
    @FXML private Button btnRegisterToLogin;

    @Autowired
    private UserService userService;

    @FXML
    private void initialize() {
        txtRegisterUserName.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() < 3) {
                showUsernameError("Minimum 3 karakter!");
            } else if (userService.usernameExists(newVal)) {
                showUsernameError("Ez a név már foglalt!");
            } else {
                clearUsernameError();
            }
        });

        pwdLoginPassword.textProperty().addListener((obs, oldText, newText) -> {
            String strength = evaluatePasswordStrength(newText);
            lblLoginPasswordStrengthResult.setText(strength);

            switch (strength) {
                case "Gyenge jelszó":
                    lblLoginPasswordStrengthResult.setStyle("-fx-text-fill: red;");
                    break;
                case "Közepes jelszó":
                    lblLoginPasswordStrengthResult.setStyle("-fx-text-fill: orange;");
                    break;
                case "Erős jelszó":
                    lblLoginPasswordStrengthResult.setStyle("-fx-text-fill: green;");
                    break;
            }
        });
    }
    
    @FXML
    private void onLoginBackLinkClicked(Event event) {
    	try {
			SceneSwitcher.switchSceneWithFade(anchPaneRegisterMain, "/fxml/Login.fxml", "Bejelentkezés");
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }

    @FXML
    private void handleRegister() {
        clearErrors();

        String username = txtRegisterUserName.getText().trim();
        String email = txtRegisterEmail.getText().trim();
        String password = pwdLoginPassword.getText().trim();

        boolean valid = true;

        if (username.length() < 3 || userService.usernameExists(username)) {
            showUsernameError("Nem megfelelő felhasználónév!");
            valid = false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            lblRegisterEmailResult.setText("Hibás e-mail formátum!");
            txtRegisterEmail.getStyleClass().add("error");
            valid = false;
        }

        if (!isStrongPassword(password)) {
            lblLoginPasswordStrengthResult.setText("Gyenge jelszó!");
            addPasswordFieldError();
            valid = false;
        }

        if (!valid) {
            lblRegisterResult.setText("Kérlek javítsd a hibákat!");
            lblRegisterResult.setStyle("-fx-text-fill: red;");
            return;
        }

        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername(username);
        dto.setEmail(email);
        dto.setPassword(password);

        if (userService.register(dto)) {
            lblRegisterResult.setText("Sikeres regisztráció! Jelentkezz be!");
            lblRegisterResult.setStyle("-fx-text-fill: green;");
        } else {
            lblRegisterResult.setText("Sikertelen regisztráció!");
            lblRegisterResult.setStyle("-fx-text-fill: red;");
        }
    }

    private void showUsernameError(String msg) {
        lblRegisterUserNameResult.setText(msg);
        if (!txtRegisterUserName.getStyleClass().contains("error")) {
            txtRegisterUserName.getStyleClass().add("error");
        }
    }

    private void clearUsernameError() {
        lblRegisterUserNameResult.setText("");
        txtRegisterUserName.getStyleClass().removeAll("error");
    }

    private void addPasswordFieldError() {
        if (!pwdLoginPassword.getStyleClass().contains("error")) {
            pwdLoginPassword.getStyleClass().add("error");
        }
    }

    private void removePasswordFieldError() {
        pwdLoginPassword.getStyleClass().removeAll("error");
    }

    private void clearErrors() {
        clearUsernameError();
        txtRegisterEmail.getStyleClass().removeAll("error");
        removePasswordFieldError();
        lblRegisterEmailResult.setText("");
        lblLoginPasswordStrengthResult.setText("");
        lblRegisterResult.setText("");
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*\\d.*");
    }

    private String evaluatePasswordStrength(String password) {
        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*\\d.*")) score++;

        switch (score) {
            case 3: return "Erős jelszó" ; 
            case 2: return "Közepes jelszó";
            default: return "Gyenge jelszó";
        }
    }
}

