package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PhotoApp;
import model.User;

public class LoginController {
    
    @FXML
    private TextField usernameTextField;

    private PhotoApp app;

    public void setApp(PhotoApp app) {
        this.app = app;
    }

    public void handleLogin(ActionEvent e) throws Exception{
        String username = usernameTextField.getText();

        // check if username is "admin"
        if (username.equals("admin")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/adminhomepage.fxml"));
            Pane root = loader.load();
            AdminHomepageController adminController = loader.getController();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            System.out.println("Before going into admin, app has " + app.getUsers().size() + " users.");
            adminController.start(stage, this.app);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            return;
        }

        // check if there is a user in app.userList with the given username
        User currentUser = null;
        System.out.println("app has " + app.getUsers().size() + " users.");
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
            homepageController.start(stage, currentUser, app); // TODO: make a User object and pass that instead
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            return;
        } else {
            // show an error alert that the user does not exist
            PhotoApp.errorAlert("Login Error", "User does not exist", "User does not exist");
        }

    }



}
