package view;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
    
    @FXML
    private TextField usernameTextField;

    public void handleLogin(ActionEvent e) throws Exception{
        String username = usernameTextField.getText();

        // check if username is "admin"
        if (username.equals("admin")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/adminhomepage.fxml"));
            Pane root = loader.load();
            AdminHomepageController adminController = loader.getController();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            adminController.start(stage);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            return;
        }

        // check if there is a folder in /data/ with the username
        boolean folderExists = false;
        File usernames[] = new File("data").listFiles();
        
        for (File f : usernames) {
            if (f.getName().equals(username)) {
                folderExists = true;
                break;
            }
        }

        if (folderExists) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/homepage.fxml"));
            Pane root = loader.load();
            HomepageController homepageController = loader.getController();
            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            homepageController.start(stage, username);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();


            /*
            Parent root = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));
            Stage window = (Stage) usernameTextField.getScene().getWindow();
            window.setScene(new Scene(root, 600, 500));
            FXMLLoader loader = new FXMLLoader(); 
            loader.setLocation(getClass().getResource("/view/homepage.fxml"));
            FXMLLoader albumListLoader = new FXMLLoader(getClass().getResource("/view/albumlist.fxml"));
            Parent homepageRoot = albumListLoader.load();
            AlbumListController albumListController = albumListLoader.getController();
            albumListController.start(window, username);
             */

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid username");
            alert.setHeaderText("The specified user does not exist. Please check your spelling and try again.");
            alert.showAndWait();
        }

    }

}
