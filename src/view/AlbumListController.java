package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class AlbumListController {
    @FXML ListView<String> albumListView;
    private ObservableList<String> obsList;

    public void start(Stage stage, String username) {
        List<String> albums = new ArrayList<>();
        
        // go through data/{username}/ and get all the album names
        // add them to the list
        
        File userDir = new File("data/" + username);
        File[] files = userDir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                albums.add(f.getName());
            }
        }


        System.out.println(albums);

        obsList = FXCollections.observableArrayList(albums);
        albumListView.setItems(obsList);

        albumListView.getSelectionModel().select(0);

        albumListView.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem(stage));

        // print out albumListView's items
        System.out.println("Albums: " + albumListView.getItems());


    }

    private void showItem(Stage mainstage) {
        System.out.println("Selected item: " + albumListView.getSelectionModel().getSelectedItem());
    }
}
