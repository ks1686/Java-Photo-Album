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
import model.PhotoApp;
import model.User;

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


    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }

    public String getTitleText() {
        return titleText.getText();
    }

    public void setAlbum(Album album) {
        selectedAlbum = album;
    }

    public void start(Stage stage, User user, PhotoApp app, Album album) {
        this.user = user;
        this.app = app;
        this.currentAlbum = album;
        albumListController.start(stage, user);
    }

    public void selectAlbum() {
        String albumName = albumListController.getSelectedAlbum();
        for (Album album : user.getAlbums()) {
            if (album.getAlbumName().equals(albumName)) {
                selectedAlbum = album;
                break;
            }
        }
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
            PhotoApp.errorAlert("Error loading gallery", "Error loading gallery", "Error loading gallery");

        }
    }
}