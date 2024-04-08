package controller;

// Java imports
import java.io.File;
import java.util.List;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// Project imports
import model.Album;
import model.Photo;

/**
 * GalleryImageViewController class to control the gallery image view
 * This class is responsible for displaying the photos in the gallery
 * and handling the selection of a photo.
 *
 * @author jacobjude
 * @author ks1686
 */
public class GalleryImageViewController  {

    @FXML private TilePane galleryImageView;

    // local variable to store the selected photo
    private Photo selectedPhoto;

    /**
     * Constructor for the GalleryImageViewController
     * @return a new instance of the GalleryImageViewController
     */
    public Photo getSelectedPhoto() {
        return this.selectedPhoto;
    }

    /**
     * Method to add a photo to the gallery
     * @param photo: the photo to add to the gallery
     */
    protected void addToGallery(Photo photo) {
        // get the photo
        String filepath = photo.getFilePath();
        Image image = new Image(new File(filepath).toURI().toString());

        // create an image view
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        // create a text view for the caption of the photo
        Text caption = new Text(photo.getCaption());
        caption.setWrappingWidth(150);
        caption.setStyle("-fx-font-size: 10px;");
        caption.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // create a vbox to hold the image view and the caption
        VBox container = new VBox(imageView, caption);
        container.setAlignment(javafx.geometry.Pos.CENTER);
        container.setSpacing(5);


        // add the container to the gallery image view
        container.setOnMouseClicked(e -> {

            // set the selected photo
            selectedPhoto = photo;

        });

        galleryImageView.getChildren().add(container);
        

    }

    /**
     * Method to start the gallery image view
     * @param album: the album to display in the gallery
     */
    public void start(Album album) {
        // set the album

        // get the photos from the album
        List<Photo> photos = album.getPhotos();

        // add the photos to the gallery image view
        for (Photo photo : photos) {

            // get the photo
            // add the photo to the gallery
            addToGallery(photo);
        }
    }

    /**
     * Method to get the gallery image view
     * @return the gallery image view
     */
    public TilePane getGalleryImageView() {
        return galleryImageView;
    }

}