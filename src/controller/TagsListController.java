package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Photo;
import model.Photos;
import model.User;

public class TagsListController {

    @FXML
    protected ListView<String> tagsListView;
    private ObservableList<String> obsList;

    @FXML
    public void start(User user, Photos app, Photo photo) {

        List<Map<String, String>> photoTags = photo.getTags();
        List<String> tags = new ArrayList<>();

        for (Map<String, String> tagType: photoTags) {
            // tagname is key and tagvalue is value
            for (Map.Entry<String, String> tag : tagType.entrySet()) {
                tags.add(tag.getKey() + ": " + tag.getValue());
            }
        }
        obsList = FXCollections.observableArrayList(tags); // create an observable list from the list of albums
        tagsListView.setItems(obsList); // set the list view to the observable list

    }

    public void addTag(String tagType, String tagValue) {
        obsList.add(tagType + ": " + tagValue);
    }

    public void deleteTag(String tagType, String tagValue) {
        obsList.remove(tagType + ": " + tagValue);
    }
}