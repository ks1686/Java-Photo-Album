package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.User;

import model.PhotoApp;


public class HomepageController {
    @FXML
    protected AlbumListController albumListController;

    private PhotoApp app;

    public void start(Stage stage, User user, PhotoApp app) { // TODO: make a User object and pass that instead
        albumListController.start(stage, user);
        this.app = app;
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
        app.logout(app);
        // close the current window
        Stage stage = (Stage) albumListController.albumListView.getScene().getWindow();
        stage.close();
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

    @FXML public void createAlbum() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter the name of the new album:");
        dialog.setContentText("Album name:");

        String albumName = dialog.showAndWait().get();
        if (albumName != null) {
            albumListController.createAlbum(albumName);
        }
        
    }

    @FXML public void searchPhotos() {
        // open a new window to search for photos
    }

    @FXML public void openAlbum() {
        // open a new window to view the selected album
    }

}
