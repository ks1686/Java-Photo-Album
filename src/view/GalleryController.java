package view;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import model.PhotoApp;
import model.User;
import model.Album;

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

}

