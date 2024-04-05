package view;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import model.PhotoApp;
import model.User;

import java.util.List;


public class GalleryController {
    @FXML private TilePane tilePane;

    private PhotoApp app;

    public void start(Stage stage, User user, PhotoApp app) {
        this.app = app;
    }

}

