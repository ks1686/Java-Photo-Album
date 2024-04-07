package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.Photos;
import model.User;
import model.Album;
import model.Photo;

import static model.Photos.errorAlert;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;


public class GalleryController {
    @FXML protected GalleryImageViewController galleryViewController;

    private Photos app;

    private Album album;
    private User user;

    public User getUser() {
        return user;
    }

    // method to start the gallery controller
    public void start(Stage stage, Photos app, Album album, User user) {
        galleryViewController.start(stage, album);
        this.app = app;
        this.album = album;
        this.user=user;
    }

    @FXML
    private Button addPhotoButton;

    @FXML
    private Button removePhotoButton;

    @FXML
    private Button setCaptionButton;

    @FXML
    private Button displaySeparatelyButton;

    @FXML
    private Button editTagsButton;

    @FXML
    private Button copyToAlbumButton;

    @FXML
    private Button moveToAlbumButton;
    public void addPhoto() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String filepath = file.getAbsolutePath();
            Photo photo = new Photo(filepath);
            album.addPhoto(photo);
            
            galleryViewController.addToGallery(photo);
            
        } else {
            errorAlert("Invalid image", null, null);
        }
    }


    @FXML
    public void removePhoto() {
        // get the selected photo
        Photo selectedPhoto = galleryViewController.getSelectedPhoto();

        // remove the photo from the album
        album.removePhoto(selectedPhoto);


        Stage stage = (Stage) removePhotoButton.getScene().getWindow();

        // clear the gallery image view
        galleryViewController.getGalleryImageView().getChildren().clear();
        galleryViewController.start(stage, album);
        // messy but works. could also do this for addphoto but it's not necessary
    }

    @FXML
    public void setCaption() {
        // TODO: Implement setCaption functionality
        Photo selectedPhoto = galleryViewController.getSelectedPhoto();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set caption");
        dialog.setHeaderText("Enter new caption for the selected photo (leave empty to delete caption):");
        dialog.setContentText("Enter caption:");

        // get the new album name
        String caption = null;
        try {
            caption = dialog.showAndWait().get();
        } catch (NoSuchElementException e) {
            return;
        }

        selectedPhoto.setCaption(caption);
    }

    @FXML
    public void displaySeparately() {
        // TODO: Implement displaySeparately functionality
    }

    @FXML
    public void editTags() {
        // TODO: Implement editTags functionality
        // should prob make a new scene or listview or something for this
    }

    // move to the choose album controller to select an album to copy the photo to
    @FXML
    public void copyToAlbum()  {
        // get the selected photo
        Photo selectedPhoto = galleryViewController.getSelectedPhoto();
        // load the choose album controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/choosealbum.fxml"));
        try {
            Pane root = loader.load();
            ChooseAlbumController chooseAlbumController = loader.getController();
            // get the current stage
            Stage stage = (Stage) copyToAlbumButton.getScene().getWindow();
            // start the choose album controller
            chooseAlbumController.start(stage, this.app, this.album, selectedPhoto, this.user);

            //set text of selectAlbumButton to "Copy to Album"
            chooseAlbumController.getSelectAlbumButton().setText("Copy to Album");
            // set the scene
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // show an error alert that the choose album screen could not be loaded
            errorAlert("Copy to Album", "Failed to load choose album screen", "Failed to load choose album screen");
        }
    }

    @FXML
    public void moveToAlbum() {
        Photo selectedPhoto = galleryViewController.getSelectedPhoto();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/choosealbum.fxml"));
        try {
            Pane root = loader.load();
            ChooseAlbumController chooseAlbumController = loader.getController();
            Stage stage = (Stage) moveToAlbumButton.getScene().getWindow();
            chooseAlbumController.start(stage, this.app, this.album, selectedPhoto, this.user);
            // get the selectAlbumButton and set the text to "Move to Album"
            chooseAlbumController.getSelectAlbumButton().setText("Move to Album");
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorAlert("Move to Album", "Failed to load choose album screen", "Failed to load choose album screen");
        }

    }

    // method to move back to the homepage controller
    @FXML
    public void backToHomepage() {
        // get the current stage
        Stage stage = (Stage) addPhotoButton.getScene().getWindow();
        // load the homepage.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/homepage.fxml"));
        try {
            Pane root = loader.load();
            HomepageController controller = loader.getController();
            controller.start(stage, this.user, this.app);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // show an error alert that the homepage screen could not be returned to
            errorAlert("Back to Homepage", "Failed to return to homepage screen", "Failed to return to homepage screen");
        }
    }
}