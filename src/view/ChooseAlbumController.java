package view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.PhotoApp;
import model.User;

import static model.PhotoApp.errorAlert;

public class ChooseAlbumController {

    @FXML
    private Text titleText;

    @FXML
    protected Button selectAlbumButton;

    @FXML
    protected AlbumListController albumListController;

    private User user;
    private Album selectedAlbum;

    private PhotoApp app;
    private Album currentAlbum;
    private Photo selectedPhoto;

    // method to start the controller
    public void start(Stage stage, PhotoApp app, Album currentAlbum, Photo selectedPhoto, User user) {
        this.app = app;
        this.currentAlbum = currentAlbum;
        this.selectedPhoto = selectedPhoto;
        this.user = user;
        titleText.setText("Choose an album to copy the photo to");
        albumListController.start(stage, user, app);


        // set the selected album to the album selected in the album list controller
        albumListController.albumListView.setOnMouseClicked(e -> {
            String albumName = albumListController.albumListView.getSelectionModel().getSelectedItem();
            albumName = albumListController.fixAlbumName(albumName);
            List<Album> albums = user.getAlbums();
            for (Album album : albums) {
                if (album.getAlbumName().equals(albumName)) {
                    selectedAlbum = album;
                }
            }
        });
    }

    // method to go back to the gallery view
    @FXML
    public void backToGallery() {
        // get the current stage
        Stage stage = (Stage) selectAlbumButton.getScene().getWindow();
        // load the gallery.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gallery.fxml"));
        try {
            Pane root = loader.load();
            GalleryController galleryController = loader.getController();
            galleryController.start(stage, this.app, this.currentAlbum, this.user);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // show an alert if there's an error
            errorAlert("Error loading gallery", "Error loading gallery", "Error loading gallery");

        }
    }

    // method to copy the previously selected photo to the selected album
    @FXML
    public void copyPhotoToAlbum() {
        // if the selected album is not null, copy the photo to the album
        // ! SELECTED ALBUM HER IS NULL
        if (selectedAlbum != null) {
            selectedAlbum.addPhoto(selectedPhoto);
            // show an info alert from the PhotoApp class
            app.infoAlert("Photo Copied", "", "Photo copied to album " + selectedAlbum.getAlbumName());
            // go back to the gallery view
            // ! ERROR OCCURS HERE AFTER PHOTO IS SUCCESSFULLY COPIED
            backToGallery();
        } else {
            // show an error alert if the selected album is null
            errorAlert("Error", "No album selected", "No album selected");
        }
    }
}