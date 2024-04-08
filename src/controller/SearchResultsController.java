package controller;

import static model.Photos.errorAlert;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Photos;
import model.User;
public class SearchResultsController {

    public AnchorPane galleryView;
  public Button backToHomepageButton;
    @FXML
    private Button createAlbumButton;

  @FXML private GalleryImageViewController galleryViewController;

    private Photos app;
    private User user;
    private Album searchResultsAlbum;


    @FXML
    public void start(Photos app, User currentUser, Album searchResultsAlbum) {
        this.app = app;
        this.user = currentUser;
        this.searchResultsAlbum = searchResultsAlbum;

        galleryViewController.start(searchResultsAlbum);
    }

    @FXML
    private void createAlbum() {
        // create a new TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Create a new album");
        dialog.setContentText("Enter the name of the new album:");

        // show the dialog and get the result
        
        dialog.showAndWait().ifPresent(albumName -> {
            // add the album to the user's list of albums
            user.createAlbum(albumName);
            Album userAlbum = user.getAlbum(albumName);
            for (Photo photo : searchResultsAlbum.getPhotos()) {
                userAlbum.addPhoto(photo);
            }
            // get the current stage
            Stage stage = (Stage) createAlbumButton.getScene().getWindow();
            // load the album.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homepage.fxml"));
            try {
                Pane root = loader.load();
                HomepageController controller = loader.getController();
                controller.start(user, app);
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
            } catch (IOException e) {
                // show an error alert that the album screen could not be loaded
                errorAlert("Create Album", "Failed to load album screen", "Failed to load album screen");
            }
        });
    }

    @FXML
    public void backToHomepage() {
        // get the current stage
        Stage stage = (Stage) createAlbumButton.getScene().getWindow();
        // load the homepage.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homepage.fxml"));
        try {
            Pane root = loader.load();
            HomepageController controller = loader.getController();
            controller.start(this.user, this.app);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            // show an error alert that the homepage screen could not be returned to
            errorAlert("Back to Homepage", "Failed to return to homepage screen", "Failed to return to homepage screen");
        }
    }
}