package controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Photos;
import model.User;
import model.Album;
import model.Photo;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.List;



public class GalleryImageViewController  {

    @FXML private TilePane galleryImageView;

    private Photos app;
    private User user;
    private Album album;

    // local variable to store the selected photo
    private Photo selectedPhoto;

    public void setSelectedPhoto(Photo photo) {
        this.selectedPhoto = photo;
    }

    public Photo getSelectedPhoto() {
        return this.selectedPhoto;
    }
    
    // method to start the gallery image view controller
    protected void addToGallery(Photo photo) {
        // get the photo
        String filepath = photo.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // create an image view
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        // create a text view for the caption of the photo
        Text caption = new Text(photo.getCaption());
        caption.setWrappingWidth(150);
        caption.setStyle("-fx-font-size: 10px;");
        caption.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // create a vbox to hold the image view and the caption
        VBox container = new VBox(imageView, caption);
        container.setAlignment(javafx.geometry.Pos.CENTER);
        container.setSpacing(5);


        // add the container to the gallery image view
        container.setOnMouseClicked(e -> {

            // set the selected photo
            selectedPhoto = photo;

        });

        galleryImageView.getChildren().add(container);
        

    }

    public void start(Stage stage, Album album) {
        // set the album
        this.album = album;

        // get the photos from the album
        List<Photo> photos = album.getPhotos();

        // add the photos to the gallery image view
        for (int i = 0; i < photos.size(); i++) {

            // get the photo
            Photo photo = photos.get(i);

            // add the photo to the gallery
            addToGallery(photo);
        }
    }

    public TilePane getGalleryImageView() {
        return galleryImageView;
    }

}