package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
        
        File userDir = new File("data/" + username); // get the user's directory
        File[] files = userDir.listFiles(); // get all the files in the user's directory
        for (File f : files) { // go through all the files
            if (f.isDirectory()) { // if it's a directory
                albums.add(f.getName()); // add the name to the list
            }
        }

        // add the number of photos in each album to the list and the range of dates
        for (int i = 0; i < albums.size(); i++) {
            File albumDir = new File("data/" + username + "/" + albums.get(i)); // get the album's directory
            File[] albumFiles = albumDir.listFiles(); // get all the files in the album's directory
            int numPhotos = 0; // initialize the number of photos
            for (File f : albumFiles) { // go through all the files
                if (f.isFile()) { // if it's a file
                    numPhotos++; // increment the number of photos
                }
            }

            albums.set(i, albums.get(i) + " (" + numPhotos + " photos)"); // add the number of photos to the album name

            // get the range of dates
            String range = "";
            if (numPhotos > 0) { // if there are photos
                File firstPhoto = albumFiles[0]; // get the first photo
                File lastPhoto = albumFiles[numPhotos - 1]; // get the last photo

                // using File class to get the last modified date of the file
                long firstDate = firstPhoto.lastModified();
                long lastDate = lastPhoto.lastModified();

                // convert the long to a date
                Date first = new Date(firstDate);
                Date last = new Date(lastDate);

                // convert the date to a string
                String firstStr = first.toString();
                String lastStr = last.toString();

                range = lastStr + " - " + firstStr; // set the range to the first and last date

                albums.set(i, albums.get(i) + " (" + range + ")"); // add the range to the album name
            } else { // if there are no photos
                range = "No photos"; // set the range to "No photos"

            }

        }


        System.out.println(albums); // print out the list of albums

        obsList = FXCollections.observableArrayList(albums); // create an observable list from the list of albums
        albumListView.setItems(obsList); // set the list view to the observable list

        albumListView.getSelectionModel().select(0); // select the first item in the list

        albumListView.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem(stage)); // add a listener to the list view

        // print out albumListView's items
        System.out.println("Albums: " + albumListView.getItems());


    }

    private void showItem(Stage mainstage) {
        System.out.println("Selected item: " + albumListView.getSelectionModel().getSelectedItem()); // print out the selected item
    }
}
