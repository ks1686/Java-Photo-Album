package view;

import java.io.IOException;
import java.util.NoSuchElementException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.User;

import model.PhotoApp;


public class HomepageController {
    @FXML
    protected AlbumListController albumListController;

    private PhotoApp app;

    // private user object
    private User user;

    public void start(Stage stage, User user, PhotoApp app) { // TODO: make a User object and pass that instead
        albumListController.start(stage, user, app);
        this.app = app;
        this.user = user;
    }

    // method to delete an album
    @FXML
    private void deleteAlbum() {
        String albumName = albumListController.getSelectedAlbum();

        // fix the album name
        albumName = albumListController.fixAlbumName(albumName);

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
        String newAlbumName = null;
        try {
            newAlbumName = dialog.showAndWait().get();
        } catch (NoSuchElementException e) {
            return;
        }

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

        String albumName = null;
        try {
            albumName = dialog.showAndWait().get();
        } catch (NoSuchElementException e) {
            return;
        }
        if (albumName != null) {
            albumListController.createAlbum(albumName);
        }
        
    }

    @FXML public void searchPhotos() {
        // open a new window to search for photos
    }

    @FXML public void openAlbum() throws IOException {
        // open the selected album
        String albumName = albumListController.getSelectedAlbum();
        albumName = albumListController.fixAlbumName(albumName);

        // get the album object
        Album album = user.getAlbum(albumName);

        // if the album is not null, open the album
        if (album != null) {
            // load the gallery controller
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/gallery.fxml"));
            Pane root = loader.load();
            GalleryController galleryController = loader.getController();

            // get the current stage
            Stage stage = (Stage) albumListController.albumListView.getScene().getWindow();
            // start the gallery controller
            
            galleryController.start(stage, app, album, user);
            // set the scene
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            return;
        } else {
            // show an error alert that the album could not be opened
            PhotoApp.errorAlert("Open Album", "Failed to open album", "Failed to open album");
        }

    }

}
