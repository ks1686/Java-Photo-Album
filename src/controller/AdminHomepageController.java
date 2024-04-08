package controller;

// Java imports
import java.io.File;
import java.util.List;
import java.util.Optional;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// Project imports
import model.Photos;
import model.User;
import static model.Photos.infoAlert;

/**
 * Controller for the admin homepage. The admin homepage displays a list of users
 * and allows the admin to create and delete users.
 * The admin homepage also allows the admin to logout.
 * The admin homepage is the first page that the admin sees after logging in.
 *
 * @author jacobjude
 * @author ks1686
 */
public class AdminHomepageController {
    public AnchorPane adminUserList;
    @FXML protected AdminUserListController adminUserListController;

    @FXML Button createUserButton;
    @FXML Button deleteUserButton;

    private Photos app;

    /**
     * Start method for the admin homepage controller.
     * @param stage: the stage for the admin homepage
     * @param app: the Photos app
     */
    public void start(Stage stage, Photos app) {
      this.app = app;
      adminUserListController.start(stage, app);
    }

    /**
     * method to create a new user
     */
    public void createUser() {
      // show a dialog box to get the username of the new user
      Optional<String> result = showItemInputDialog((Stage) createUserButton.getScene().getWindow());
      // if the user entered a username (need to iterate through getUsers() to check if the username already exists)
      if (result.isPresent()) {
          String username = result.get();
          List<User> users = app.getUsers();
          for (User user : users) {
              if (user.getUsername().equals(username)) {
                  // show an alert that the username already exists
                  // errorAlert needs 3 arguments: title, header, and content
                  Photos.errorAlert("Error", "Username Already Exists", "The username you entered already exists. Please enter a different username.");
                  return;
              }
          }
          // create a new user with the entered username
          User newUser = new User(username);
          // add the user to the list of users
          users.add(newUser);
          // add the user to the list view
          adminUserListController.obsList.add(username);
          // create a new folder in data/users/ with the username
          File userDir = new File("data/users/" + username);
          userDir.mkdir();

          // show an alert that the user was created successfully
          infoAlert("User Created", "User Created Successfully", "The user " + username + " was created successfully.");

      }


    }

    /**
     * method to delete a user
     */
    public void deleteUser() {
      // get the selected username from the list view
      String selectedUsername = adminUserListController.adminUserListView.getSelectionModel().getSelectedItem();

      // if the selected username is not null
      if (selectedUsername != null) {
          // get the list of users
          List<User> users = app.getUsers();
          // iterate through the list of users
          for (User user : users) {
              // if the username of the user is the same as the selected username
              if (user.getUsername().equals(selectedUsername)) {
                  // remove the user from the list of users
                  users.remove(user);
                  // remove the user from the list view
                  adminUserListController.obsList.remove(selectedUsername);
                  // delete the folder in data/users/ with the username
                  File userDir = new File("data/users/" + selectedUsername);
                  recursiveDeleteDir(userDir);
                  // show an alert that the user was deleted successfully
                  infoAlert("User Deleted", "User Deleted Successfully", "The user " + selectedUsername + " was deleted successfully.");
                  return;
              }
          }
      }
    }

    /**
     * method to recursively delete a directory
     * @param dir: the directory to delete
     */
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

    /**
     * method to show an input dialog box to get the username of the new user
     * @param mainStage: the main stage
     * @return the username of the new user
     */
    private Optional<String> showItemInputDialog(Stage mainStage) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.initOwner(mainStage); dialog.setTitle("Create New User");
      dialog.setHeaderText("Enter Username of New User");
      dialog.setContentText("Username: ");
      return dialog.showAndWait();
    }

    /**
     * method to logout
     */
    @FXML
    public void logout() {
      // call the logout method in the PhotoApp class
      app.logout(app);
      // close the current window
      Stage stage = (Stage) createUserButton.getScene().getWindow();
      stage.close();
    }
}
