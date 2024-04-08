package controller;

// Java imports
import java.util.List;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Project imports
import model.Album;
import model.Photo;
import model.Photos;
import model.User;
import static model.Photos.errorAlert;

/**
 * Controller for the choose album view.
 * This view is used to choose an album to copy or move a photo to.
 * The user can select an album from the list of albums.
 * The user can then click the "Copy to Album" button to copy the photo to the selected album.
 * The user can also click the "Move to Album" button to move the photo to the selected album.
 * The user can also click the "Back to Photos" button to go back to the photos view.
 *
 * @author jacobjude
 * @author ks1686
 */
public class ChooseAlbumController {

  public AnchorPane albumList;
  public Button backToPhotos;
    @FXML
    private Text titleText;

    @FXML
    protected Button selectAlbumButton;

    @FXML
    protected AlbumListController albumListController;

    private User user;
    private Album selectedAlbum;

    private Photos app;
    private Album currentAlbum;
    private Photo selectedPhoto;

    /**
     * Method to start the controller
     * @param app: Photos
     * @param currentAlbum: Album
     * @param selectedPhoto: the photo selected
     * @param user: the current user
     */
    public void start(Photos app, Album currentAlbum, Photo selectedPhoto, User user) {
        this.app = app;
        this.currentAlbum = currentAlbum;
        this.selectedPhoto = selectedPhoto;
        this.user = user;
        titleText.setText("Choose an album");
        albumListController.start(user, app);


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

    /**
     * Method to go back to the photos view
     */
    @FXML
    public void backToGallery() {
        // get the current stage
        Stage stage = (Stage) selectAlbumButton.getScene().getWindow();
        // load the gallery.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gallery.fxml"));
        try {
            Pane root = loader.load();
            GalleryController galleryController = loader.getController();
            galleryController.start(this.app, this.currentAlbum, this.user);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // show an alert if there's an error
            errorAlert("Error loading gallery", "Error loading gallery", "Error loading gallery");

        }
    }

    /**
     * Method to copy or move the photo to the selected album
     */
    @FXML
    public void moveOrCopyToAlbum() {
        // if the selected album is not null, copy the photo to the album
        // check the text of the button. if it is "Copy to Album", copy the photo to the album
        String buttonText = selectAlbumButton.getText();
        Photo selectedPhoto = this.selectedPhoto;
        Album selectedAlbum = this.selectedAlbum;
        Album currentAlbum = this.currentAlbum;
        if (selectedAlbum == null) {
            errorAlert("Error", "No album selected", "No album selected");
            return;
        }

        if (selectedAlbum.getPhotos().contains(selectedPhoto)) {
            errorAlert("Error", "Photo already in album", "Photo already in album");
            return;
        } else if (selectedAlbum.equals(currentAlbum)) {
            errorAlert("Error", "Cannot copy to same album", "Cannot copy to same album");
            return;
        }

        if (buttonText.equals("Copy to Album")) {
            copyToAlbum(selectedPhoto, selectedAlbum);
            Photos.infoAlert("Photo Copied", "", "Photo copied to album " + selectedAlbum.getAlbumName());
        } else if (buttonText.equals("Move to Album")) {
            moveToAlbum(selectedPhoto, currentAlbum, selectedAlbum);
            Photos.infoAlert("Photo moved", "", "Photo moved to album " + selectedAlbum.getAlbumName());
        }
        backToGallery();

    }

    /**
     * Method to copy a photo to an album
     * @param photo: the photo to copy
     * @param album: the album to copy the photo to
     */
    public void copyToAlbum(Photo photo, Album album) {
        album.addPhoto(photo);
    }

    /**
     * Method to move a photo to an album
     * @param photo: the photo to move
     * @param oldAlbum: the album the photo is currently in
     * @param album: the album to move the photo to
     */
    public void moveToAlbum(Photo photo, Album oldAlbum, Album album) {
        album.addPhoto(photo);
        oldAlbum.removePhoto(photo);

    }

    /**
     * Method to get the selected album
     * @return selectedAlbum button
     */
    public Button getSelectAlbumButton() {
        return selectAlbumButton;
    }
}