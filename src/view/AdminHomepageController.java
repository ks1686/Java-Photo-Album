package view;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class AdminHomepageController {
    @FXML
    protected AdminUserListController adminUserListController;

    @FXML Button createUserButton;
    @FXML Button deleteUserButton;

    public void start(Stage stage) {
        adminUserListController.start(stage);
    }

    public void createUser() {
        System.out.println("create user button clicked");
        Stage stage = (Stage) createUserButton.getScene().getWindow();
        Optional<String> result = showItemInputDialog(stage);
        if (result.isPresent()) { 
            String username = result.get();
            // create a new folder in data/{username}
            File userDir = new File("data/" + username);
            userDir.mkdir();
            adminUserListController.obsList.add(username);
        }

    }

    public void deleteUser() {
        System.out.println("delete user button clicked");
        // get the selected user from the list
        // delete the folder data/{username}
        String username = adminUserListController.adminUserListView.getSelectionModel().getSelectedItem();

        if (username == null) {
            return;
        }

        // remove the user from the list
        adminUserListController.obsList.remove(username);

        // delete the folder
        File userDir = new File("data/" + username);
        recursiveDeleteDir(userDir);

    }

    private void recursiveDeleteDir(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    recursiveDeleteDir(f);
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
    }

    private Optional<String> showItemInputDialog(Stage mainStage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(mainStage); dialog.setTitle("Create New User");
        dialog.setHeaderText("Enter Username of New User");
        dialog.setContentText("Username: ");
        Optional<String> result = dialog.showAndWait();
        return result;
    }

}
