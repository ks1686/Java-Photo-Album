package controller;

// Java imports
import java.io.File;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

// Project imports
import model.Album;
import model.Photo;

/**
 * Controller class for the slideshow view
 * This class is responsible for the functionality of the slideshow view
 * It allows the user to view the photos in the album in a slideshow
 * The user can go to the next photo or the previous photo
 *
 * @author ks1686
 */
public class SlideshowViewController {

    private Album currentAlbum;
    private Photo currentPhoto;
    private int currentIndex;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;

    // the imageView in the slideshow view
    @FXML
    private ImageView slideshowImageView;

    /**
     * Method to start the slideshow view
     * @param selectedPhoto: the photo that the user selected
     * @param currentAlbum: the album that the selected photo is in
     */
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

    /**
     * Method to go to the next photo (disable the button if there is no next photo)
     */
    @FXML
    public void nextPhoto() {
        // get the next photo
        currentIndex++;
        if (currentIndex >= currentAlbum.getPhotos().size()) {
            currentIndex = 0;
        }
        currentPhoto = currentAlbum.getPhotos().get(currentIndex);

        // disable the button if there is no next photo
        if (currentIndex == currentAlbum.getPhotos().size() - 1) {
            nextButton.setDisable(true);
        }
        // enable the previous button
        previousButton.setDisable(false);

        // get the photo
        String filepath = currentPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view to the selected photo
        slideshowImageView.setImage(image);

    }

    /**
     * Method to go to the previous photo (disable the button if there is no previous photo)
     */
    @FXML
    public void previousPhoto() {
        // get the previous photo
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = currentAlbum.getPhotos().size() - 1;
        }
        currentPhoto = currentAlbum.getPhotos().get(currentIndex);

        // disable the button if there is no previous photo
        if (currentIndex == 0) {
            previousButton.setDisable(true);
        }
        // enable the next button
        nextButton.setDisable(false);

        // get the photo
        String filepath = currentPhoto.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // set the image view to the selected photo
        slideshowImageView.setImage(image);
    }

}
