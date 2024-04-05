package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.PhotoApp;
import model.User;
import model.Album;
import model.Photo;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.util.List;



public class GalleryImageViewController  {

    @FXML private TilePane galleryImageView;

    private PhotoApp app;
    private User user;
    private Album album;

    // method to start the gallery image view controller
    public void start(Stage stage, Album album) {

        // get the photos from the album
        List<Photo> photos = album.getPhotos();

        // add the photos to the gallery image view
        for (int i = 0; i < photos.size(); i++) {

            // get the photo
            Photo photo = photos.get(i);
            String filepath = photo.getFilePath();
            Image image = new Image(new File(filepath).toURI().toString());

            // ! print the image filepath to the console
            System.out.println(filepath);

            // create an image view
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);

            // add the image view to the gallery image view
            galleryImageView.getChildren().add(imageView);
        }

    }

}