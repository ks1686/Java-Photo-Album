package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Photos;
import model.User;
import model.Album;
import model.Photo;
import javafx.scene.layout.TilePane;

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

        // add the image view to the gallery image view
        imageView.setOnMouseClicked(e -> {

            // set the selected photo
            selectedPhoto = photo;
            // ! print the photo filepath to the console
            System.out.println(photo.getFilePath());
        });

        galleryImageView.getChildren().add(imageView);
        

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