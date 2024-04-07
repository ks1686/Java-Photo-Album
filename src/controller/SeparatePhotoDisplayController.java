package controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Photo;

public class SeparatePhotoDisplayController {

    @FXML private ImageView imageView;
    @FXML private Label captionLabel;
    @FXML private Label dateTimeLabel;
    @FXML private Label tagsLabel;
    public void start(Photo selectedPhoto) {
        // get the selected photo file path
        String filepath = selectedPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view
        imageView.setImage(image);
        // set the caption label
        captionLabel.setText(selectedPhoto.getCaption());


        // set the date label
        dateTimeLabel.setText(selectedPhoto.getDate().getTime().toString());

        // set the tags label
        tagsLabel.setText(selectedPhoto.getTags().toString());

    }
}

