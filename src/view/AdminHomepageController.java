package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminHomepageController {
    @FXML
    protected AdminUserListController adminUserListController;

    @FXML Button createUserButton;
    @FXML Button deleteUserButton;

    public void start(Stage stage) {
        adminUserListController.start(stage);
    }

}
