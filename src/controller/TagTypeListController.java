package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Photo;
import model.Photos;
import model.User;

public class TagTypeListController {

    @FXML
    protected ListView<String> tagTypeListView;
    private ObservableList<String> obsList;

    @FXML
    public void start(User user, Photos app, Photo photo) {
    

        List<String> tagTypes = user.getTagTypes();
        List<String> tags = new ArrayList<>();
        for (String tagType: tagTypes) {
            // tagname is key and tagvalue is value
            tags.add(tagType);
            
        }
        obsList = FXCollections.observableArrayList(tags); // create an observable list from the list of albums
        tagTypeListView.setItems(obsList); // set the list view to the observable list

    }

    public void addTagType(String tagType) {
        obsList.add(tagType);
    }

    public void deleteTagType(String tagType) {
        obsList.remove(tagType);
    }
}