package view;

import java.io.File;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import model.PhotoApp;
import model.User;

import static model.PhotoApp.infoAlert;

public class AdminHomepageController {
    @FXML
    protected AdminUserListController adminUserListController;

    @FXML Button createUserButton;
    @FXML Button deleteUserButton;

    private PhotoApp app;

    public void start(Stage stage, PhotoApp app) {
        this.app = app;
        adminUserListController.start(stage, app);
    }

    public void createUser() {
        System.out.println("create user button clicked");
        Stage stage = (Stage) createUserButton.getScene().getWindow();
        Optional<String> result = showItemInputDialog(stage);
        if (result.isPresent()) {  // TODO: make this use the User class
            String username = result.get();
            // get list of existing usernames from adminUserListController
            for (User user : app.getUsers()) {
                if (user.getUsername().equals(username)) {
                    infoAlert("Error", "Invalid Username", "User already exists.");
                    return;
                }
            }

            if (username == null || username.equals("admin") || username.equals("")) {
                infoAlert("Error", "Invalid Username", "Cannot create a user with the username '" + username + "'.");
                return;
            }
            
            // create a new folder in data/users/{username} 
            File userDir = new File("data/users/" + username);
            userDir.mkdirs();
            adminUserListController.obsList.add(username);
            User user = new User(username);
            System.out.println("Adding user to app");
            app.getUsers().add(user);
            System.out.println("Length of users list: " + app.getUsers().size());

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

    // method to logout
    @FXML
    public void logout() {
        // print out a message saying we are logging out of the user
        System.out.println("Logging out of admin");

        // return to the login screen
        PhotoApp app = new PhotoApp();
        try {
            app.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // show an alert that we have logged out
        // infoAlert needs 3 arguments: title, header, and content
        infoAlert("Logout", "Logout Successful", "You have successfully logged out of the admin account.");

        // close the current window
        Stage stage = (Stage) createUserButton.getScene().getWindow();
        stage.close();

    }

}
