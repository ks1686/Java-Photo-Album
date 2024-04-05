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

import java.util.List;



public class GalleryImageViewController  {

    @FXML private TilePane galleryImageView;

    private PhotoApp app;
    private User user;
    private Album album;

    // method to start the gallery image view controller
    public void start(Stage stage, Album album) {
        // ! Print status messages
        System.out.println("test");

    }


}