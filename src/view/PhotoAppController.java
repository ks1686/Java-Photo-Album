package view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PhotoAppController {

    @FXML
    private TextField usernameTextField;

    public void handleLogin(ActionEvent e) throws Exception{
        String username = usernameTextField.getText();

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
            Parent root = FXMLLoader.load(getClass().getResource("/view/homepage.fxml"));
            Stage window = (Stage) usernameTextField.getScene().getWindow();
            window.setScene(new Scene(root, 600, 500));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid username");
            alert.setHeaderText("The specified user does not exist. Please check your spelling and try again.");
            alert.showAndWait();

        }

    }
}
