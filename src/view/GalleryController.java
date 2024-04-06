package view;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.PhotoApp;
import model.User;
import model.Album;
import model.Photo;

import static model.PhotoApp.errorAlert;

import java.io.File;
import java.util.List;


public class GalleryController {
    @FXML protected GalleryImageViewController galleryViewController;

    private PhotoApp app;

    private Album album;

    // method to start the gallery controller
    public void start(Stage stage, PhotoApp app, Album album) {
        galleryViewController.start(stage, album);
        this.app = app;
        this.album = album;
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

   @FXML
    public void addPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String filepath = file.getAbsolutePath();
            Photo photo = new Photo(filepath);
            album.addPhoto(photo);
            
            Image image = new Image(new File(filepath).toURI().toString());

            // ! print the image filepath to the console
            System.out.println(filepath);

            // create an image view
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);

            // add the image view to the gallery image view
            // first get the galleryImageView
            TilePane galleryImageView = galleryViewController.getGalleryImageView();
            galleryImageView.getChildren().add(imageView);
            imageView.setOnMouseClicked(e -> {

                // set the selected photo
                galleryViewController.setSelectedPhoto(photo);

                // ! print the photo filepath to the console
                System.out.println(photo.getFilePath());
            });
        } else {
            errorAlert("Invalid image", null, null);
        }
    }


    @FXML
    public void removePhoto() {
        // TODO: Implement removePhoto functionality
    }

    @FXML
    public void setCaption() {
        // TODO: Implement setCaption functionality
    }

    @FXML
    public void displaySeparately() {
        // TODO: Implement displaySeparately functionality
    }

    @FXML
    public void editTags() {
        // TODO: Implement editTags functionality
    }

    @FXML
    public void copyToAlbum() {
        // TODO: Implement copyToAlbum functionality
    }

    @FXML
    public void moveToAlbum() {
        // TODO: Implement moveToAlbum functionality
    }


}

