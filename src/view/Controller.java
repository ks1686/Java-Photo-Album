package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

    @FXML
    private TextField usernameTextField;

    @FXML
    public void initialize() {
        usernameTextField.setOnKeyPressed(event -> handleKeyPress(event));
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    private void handleLogin() {
        String username = usernameTextField.getText();
        System.out.println(username);

        // for now it just prints the username to the console
    }
}
