package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class AdminUserListController {
    @FXML ListView<String> adminUserListView;
    public ObservableList<String> obsList;

    public void start(Stage stage) {
        List<String> users = new ArrayList<>();
        
        // go through data/{username}/ and get all the album names
        // add them to the list
        
        File userDir = new File("data");
        File[] files = userDir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                users.add(f.getName());
            }
        }

        obsList = FXCollections.observableArrayList(users);
        adminUserListView.setItems(obsList);

        // set the listener
        adminUserListView.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem(stage));

    }
    private void showItem(Stage mainstage) {
        String selectedUser = adminUserListView.getSelectionModel().getSelectedItem();
        Button deleteUserButton = (Button) mainstage.getScene().lookup("#deleteUserButton");
        if (selectedUser != null) {
            deleteUserButton.setDisable(false);
        } else {
            deleteUserButton.setDisable(true);
        }
    }
}
