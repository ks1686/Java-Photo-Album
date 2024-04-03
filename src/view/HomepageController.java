package view;

import javafx.fxml.FXML;
import javafx.stage.Stage;


public class HomepageController {
    @FXML
    protected AlbumListController albumListController;

    public void start(Stage stage, String username) {
        albumListController.start(stage, username);
    }
}
