package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import model.Photos;
import model.User;
import model.Album;
import model.Photo;

import static model.Photos.errorAlert;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;


public class SeparatePhotoDisplayController {

    @FXML private ImageView imageView;
    @FXML private Label captionLabel;
    @FXML private Label dateLabel;
    @FXML private Label tagsLabel;
    public void start(Stage stage, Photos app, Album album, Photo selectedPhoto, User user) {
        // get the selected photo file path
        String filepath = selectedPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view
        imageView.setImage(image);

        // set the caption label
        captionLabel.setText(selectedPhoto.getCaption());

        // set the date label
        dateLabel.setText(selectedPhoto.getDate().getTime().toString());

        // set the tags label
        tagsLabel.setText(selectedPhoto.getTags().toString());
    }
}

