package controller;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import model.Photo;
import model.Photos;
import model.User;

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

    @FXML
    public void start(User user, Photos app, Photo photo) {
        tagsListController.start(user, app, photo);
        tagTypeListController.start(user, app, photo);
        this.user = user;
        this.photo = photo;

        tagsListController.tagsListView.setOnMouseClicked(e -> {
            selectedTag = tagsListController.tagsListView.getSelectionModel().getSelectedItem();
            System.out.println("Selected tag: " + selectedTag);
        });

        tagTypeListController.tagTypeListView.setOnMouseClicked(e -> {
            
            selectedTagType = tagTypeListController.tagTypeListView.getSelectionModel().getSelectedItem();
            System.out.println("Selected tagtype: " + selectedTagType);
        });
    }

    @FXML private void deleteTagType() {
        if (selectedTagType == null) {
            Photos.errorAlert("Error", "No tag type selected.", "Please select a tag type to delete.");
            return;
        }
        user.getTagTypes().remove(selectedTagType);
        tagTypeListController.deleteTagType(selectedTagType);
        Photos.infoAlert("Success", "Tag type deleted successfully.", "The tag type has been removed from the photo.");
    }

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

    @FXML
    private void deleteSelectedTag() {
        String tagType = selectedTag.split(":")[0].strip();
        String tagValue = selectedTag.split(":")[1].strip();

        this.photo.deleteTag(tagType, tagValue);
        tagsListController.deleteTag(tagType, tagValue);
        Photos.infoAlert("Success", "Tag " + selectedTag + " (previously selected) has been deleted successfully.", "The tag has been removed from the photo.");
    }
}