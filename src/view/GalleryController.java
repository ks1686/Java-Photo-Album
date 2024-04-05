package view;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import model.PhotoApp;
import model.User;
import model.Album;

import java.util.List;


public class GalleryController {
    @FXML protected GalleryImageViewController galleryImageViewController;

    private PhotoApp app;

    private Album album;

    // method to start the gallery controller
    public void start(Stage stage, PhotoApp app, Album album) {
        galleryImageViewController.start(stage, album); // ! Error here, this.imageView is null
        this.app = app;
        this.album = album;
    }

}

