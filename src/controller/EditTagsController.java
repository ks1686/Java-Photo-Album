package controller;

// Java imports
import java.util.Optional;

// JavaFX imports
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

// Project imports
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Photos;
import model.User;

/**
 * Controller class for the EditTags view.
 * This class is responsible for handling user input and updating the view.
 * It is also responsible for updating the model.
 *
 * @author jacobjude
 */
public class EditTagsController {

    @FXML
    private Button addTagButton;

    @FXML
    private Button deleteSelectedTagButton;

    @FXML
    private Button createTagTypeButton;

    @FXML private Button deleteTagTypeButton;

    @FXML TagsListController tagsListController;
    @FXML TagTypeListController tagTypeListController;

    private User user; 
    private Photo photo;

    private String selectedTag;
    private String selectedTagType;
    private Photos app;
    private Album album;

    /**
     * Initializes the controller class.
     * @param user: the current user
     * @param app: the main application
     * @param photo: the photo to edit tags for
     */
    @FXML
    public void start(User user, Photos app, Photo photo, Album album) {
        tagsListController.start(user, app, photo);
        tagTypeListController.start(user, app, photo);
        this.user = user;
        this.photo = photo;
        this.app = app;
        this.album = album;

        tagsListController.tagsListView.setOnMouseClicked(e -> {
            selectedTag = tagsListController.tagsListView.getSelectionModel().getSelectedItem();
        });

        tagTypeListController.tagTypeListView.setOnMouseClicked(e -> {
            
            selectedTagType = tagTypeListController.tagTypeListView.getSelectionModel().getSelectedItem();
        });
    }

    /**
     * Deletes the selected tag type.
     */
    @FXML private void deleteTagType() {
        if (selectedTagType == null) {
            Photos.errorAlert("Error", "No tag type selected.", "Please select a tag type to delete.");
            return;
        }
        user.getTagTypes().remove(selectedTagType);
        tagTypeListController.deleteTagType(selectedTagType);
        Photos.infoAlert("Success", "Tag type deleted successfully.", "The tag type has been removed from the photo.");
    }

    /**
     * Adds a tag to the photo.
     */
    @FXML
    private void addTag() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adding Tag");
        dialog.setHeaderText("Previously tag type: " + selectedTagType);
        dialog.setContentText("Enter tag value:");
        dialog.setContentText("Tag Value:");
        Optional<String> tagValueResult = dialog.showAndWait();
        if (tagValueResult.isPresent()) {
            String tagValue = tagValueResult.get();
            if (tagValue.isEmpty()) {
                Photos.errorAlert("Error", "Tag Value cannot be empty.", "Please enter a valid tag value.");
            }
            this.photo.addTag(selectedTagType.strip(), tagValue.strip());
            // add to obsList in TagsListController
            tagsListController.addTag(selectedTagType.strip(), tagValue.strip());

        }
        Photos.infoAlert("Success", "Tag added successfully.", "The tag has been added to the photo.");
    }

    /**
     * Creates a new tag type.
     */
    @FXML
    private void createTagType() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Tag type");
        dialog.setContentText("Enter tag name:");
        dialog.setContentText("Tag Value:");
        Optional<String> tagType = dialog.showAndWait();
        if (tagType.isPresent()) {
            String tagTypeValue = tagType.get();
            if (tagTypeValue.isEmpty()) {
                Photos.errorAlert("Error", "Tag Type cannot be empty.", "Please enter a valid tag value.");
            }
            user.getTagTypes().add(tagTypeValue.strip());
            tagTypeListController.addTagType(tagTypeValue);

        }
        Photos.infoAlert("Success", "Tag type added successfully.", "You can now use this tag type to tag photos.");
    }

    /**
     * Deletes the selected tag.
     */
    @FXML
    private void deleteSelectedTag() {
        String tagType = selectedTag.split(":")[0].strip();
        String tagValue = selectedTag.split(":")[1].strip();

        this.photo.deleteTag(tagType, tagValue);
        tagsListController.deleteTag(tagType, tagValue);
        Photos.infoAlert("Success", "Tag " + selectedTag + " (previously selected) has been deleted successfully.", "The tag has been removed from the photo.");
    }

    /**
     * Navigates back to the gallery view.
     */
    @FXML
    private void backToGallery() {
        // get the current stage
        Stage stage = (Stage) addTagButton.getScene().getWindow();
        // load the gallery.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gallery.fxml"));
        try {
            Pane root = loader.load();
            GalleryController galleryController = loader.getController();
            galleryController.start(this.app, this.album, this.user);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            // show an alert if there's an error
            Photos.errorAlert("Error loading gallery", "Error loading gallery", "Error loading gallery");

        }
    }
}