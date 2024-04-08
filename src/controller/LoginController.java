package controller;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Project imports
import model.Photos;
import model.User;

/**
 * Controller for the login screen
 * Handles the login button being clicked
 * If the username is "admin", the admin homepage is loaded
 * If the username is a user in the app, the homepage is loaded
 * If the username is not a user in the app, an error alert is shown
 *
 * @author jacobjude
 */
public class LoginController {

  public Text loginText;
    @FXML
    private TextField usernameTextField;

    private Photos app;

    /**
     * Sets the app for the controller
     * @param app: the app to set
     */
    public void setApp(Photos app) {
        this.app = app;
    }

    /**
     * Handles the login button being clicked
     * @throws Exception: if the fxml file cannot be found
     */
    public void handleLogin() throws Exception{
        String username = usernameTextField.getText();

        // check if username is "admin"
        if (username.equals("admin")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/adminhomepage.fxml"));
            Pane root = loader.load();
            AdminHomepageController adminController = loader.getController();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            adminController.start(stage, this.app);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            return;
        }

        // check if there is a user in app.userList with the given username
        User currentUser = null;
        for (User user : app.getUsers()) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
                break;
            }
        }
        if (currentUser != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/homepage.fxml"));
            Pane root = loader.load();
            HomepageController homepageController = loader.getController();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            homepageController.start(currentUser, app); // TODO: make a User object and pass that instead
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } else {
            // show an error alert that the user does not exist
            Photos.errorAlert("Login Error", "User does not exist", "User does not exist");
        }

    }



}
