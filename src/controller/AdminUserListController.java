package controller;

// Java imports
import java.util.ArrayList;
import java.util.List;

// JavaFX imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

// Project imports
import model.Photos;
import model.User;

/**
 * Controller for the admin user list. The admin user list displays a list of users
 * and allows the admin to delete users or create a new user.
 * The admin user list is a part of the admin homepage.
 * The admin user list is the first page that the admin sees after logging in.
 *
 * @author jacobjude
 */
public class AdminUserListController {
    @FXML ListView<String> adminUserListView;
    public ObservableList<String> obsList;

    /**
     * Start method for the admin user list controller.
     * @param stage: the stage to display the admin user list
     * @param app: the Photos app
     */
    public void start(Stage stage, Photos app) {
        List<String> users = new ArrayList<>();
        
        for (User user : app.getUsers()) {
            users.add(user.getUsername());
        }
        obsList = FXCollections.observableArrayList(users);
        adminUserListView.setItems(obsList);

        // set the listener
        adminUserListView.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem(stage));

    }

    /**
     * Method to handle the enable/disable of the delete user button.
     * @param mainstage: the stage to display the admin user list
     */
    private void showItem(Stage mainstage) {
        String selectedUser = adminUserListView.getSelectionModel().getSelectedItem();
        Button deleteUserButton = (Button) mainstage.getScene().lookup("#deleteUserButton");
        deleteUserButton.setDisable(selectedUser == null);
    }
}
