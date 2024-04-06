package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import model.PhotoApp;
import model.User;
import model.Album;
import model.Photo;

import static model.PhotoApp.errorAlert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.io.InputStream;


public class GalleryController {
    @FXML protected GalleryImageViewController galleryViewController;

    private PhotoApp app;

    private Album album;
    private User user;

    public User getUser() {
        return user;
    }

    // method to start the gallery controller
    public void start(Stage stage, PhotoApp app, Album album, User user) {
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
            
            // Image image = new Image(new File(filepath).toURI().toString());
            InputStream stream = new FileInputStream(filepath);
            Image image = new Image(stream);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);

            // ! print the image filepath to the console
            System.out.println(filepath);

    
            // add the image view to the gallery image view
            // first get the galleryImageView
            TilePane galleryImageView = galleryViewController.getGalleryImageView();
            imageView.setOnMouseClicked(e -> {

                // set the selected photo
                galleryViewController.setSelectedPhoto(photo);

                // ! print the photo filepath to the console
                System.out.println(photo.getFilePath());
            });
            galleryImageView.getChildren().add(imageView);
            
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
        dialog.setHeaderText("Enter new caption (leave empty to delete caption):");
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

    @FXML
    public void copyToAlbum() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/choosealbum.fxml"));
        Pane root = loader.load();
        ChooseAlbumController chooseAlbumController = loader.getController();

        // get the current stage
        Stage stage = (Stage) galleryViewController.getGalleryImageView().getScene().getWindow();
        // start the gallery controller
        chooseAlbumController.start(stage, user, app, album);
        // set the scene
        // get the text from chooseAlbumController titleText
        chooseAlbumController.setTitleText("Choose an Album");
        // now set the buttons text to "Copy to Album"
        chooseAlbumController.selectAlbumButton.setText("Copy to Album");
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();

        // TODO: now somehow get the selected album from chooseAlbumController and then do the copyTo logic?

        // on

        
    }

    @FXML
    public void moveToAlbum() {
        // TODO: Implement moveToAlbum functionality
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