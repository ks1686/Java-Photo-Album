package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.User;


public class HomepageController {
    @FXML
    protected AlbumListController albumListController;

    public void start(Stage stage, User user) { // TODO: make a User object and pass that instead
        albumListController.start(stage, user);
    }

    // method to delete an album
    @FXML
    private void deleteAlbum() {
        String albumName = albumListController.getSelectedAlbum();

        // if album is not null, delete the album
        if (albumName != null) {
            albumListController.deleteAlbum(albumName);
        }
    }

    // method to log out
    @FXML
    private void logout() {
        // go back to the login screen
        albumListController.logout();
    }

    // method to rename an album
    // button press opens a dialog box to enter the new album name
    @FXML
    private void renameAlbum() {
        // open a text input dialog box
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename Album");
        dialog.setHeaderText("Enter the new album name:");
        dialog.setContentText("New album name:");

        // get the new album name
        String newAlbumName = dialog.showAndWait().get();

        // get the selected album
        String albumName = albumListController.getSelectedAlbum();

        // if the album name is not null, rename the album
        if (albumName != null) {
            albumListController.renameAlbum(albumName, newAlbumName);
        }
    }

}
