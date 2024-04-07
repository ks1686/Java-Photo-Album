package controller;

import javafx.fxml.FXML;
import model.Album;
import model.Photo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


public class SlideshowViewController {

    private Album currentAlbum;
    private Photo currentPhoto;
    private int currentIndex;

    // the imageView in the slideshow view
    @FXML
    private ImageView slideshowImageView;
    public void start(Photo selectedPhoto, Album currentAlbum) {
        this.currentAlbum = currentAlbum;
        this.currentPhoto = selectedPhoto;

        // get the index of the selected photo on the album
        currentIndex = currentAlbum.getPhotos().indexOf(selectedPhoto);

        // get the photo
        String filepath = selectedPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view to the selected photo
        slideshowImageView.setImage(image);

    }

    // method to go to the next photo
    @FXML
    public void nextPhoto() {
        // get the next photo
        currentIndex++;
        if (currentIndex >= currentAlbum.getPhotos().size()) {
            currentIndex = 0;
        }
        currentPhoto = currentAlbum.getPhotos().get(currentIndex);

        // get the photo
        String filepath = currentPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view to the selected photo
        slideshowImageView.setImage(image);

    }

    // method to go to the previous photo
    @FXML
    public void previousPhoto() {
        // get the previous photo
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = currentAlbum.getPhotos().size() - 1;
        }
        currentPhoto = currentAlbum.getPhotos().get(currentIndex);

        // get the photo
        String filepath = currentPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view to the selected photo
        slideshowImageView.setImage(image);
    }
}
