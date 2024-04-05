package view;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Album;
import model.PhotoApp;
import model.User;

import static model.PhotoApp.errorAlert;
import static model.PhotoApp.infoAlert;


public class AlbumListController {
    @FXML ListView<String> albumListView;
    private ObservableList<String> obsList;
    private User user;

    // method to start the album list controller
    public void start(Stage stage, User user) {

        List<Album> albums = user.getAlbums();
        List<String> albumNames = new ArrayList<>();
        this.user = user;

        // add the number of photos in each album to the list and the range of dates
        for (int i = 0; i < albums.size(); i++) {
            
            Album album = albums.get(i);
            String albumName = album.getAlbumName();
            int numPhotos = album.getPhotos().size();
            Calendar startDate = album.getStartDate();
            Calendar endDate = album.getEndDate();

            // add the number of photos and the range of dates to the album name
            // format Calendar to a String

            String startDateString = "N/A";
            String endDateString = "N/A";
            if (startDate != null) {
                startDateString = startDate.get(Calendar.MONTH) + "/" + startDate.get(Calendar.DAY_OF_MONTH) + "/" + startDate.get(Calendar.YEAR);
            }

            if (endDate != null) {
                endDateString = endDate.get(Calendar.MONTH) + "/" + endDate.get(Calendar.DAY_OF_MONTH) + "/" + endDate.get(Calendar.YEAR);
            }
            
            
            albumName += " (" + numPhotos + " photos, " + startDateString + " - " + endDateString + ")";
            albumNames.add(albumName);
        }


        System.out.println(albums); // print out the list of albums

        obsList = FXCollections.observableArrayList(albumNames); // create an observable list from the list of albums
        albumListView.setItems(obsList); // set the list view to the observable list

        albumListView.getSelectionModel().select(0); // select the first item in the list

        albumListView.getSelectionModel().selectedIndexProperty().addListener((obsList, oldVal, newVal) -> showItem(stage)); // add a listener to the list view

        // print out albumListView's items
        System.out.println("Albums: " + albumListView.getItems());


    }

    private void showItem(Stage mainstage) {
        System.out.println("Selected item: " + albumListView.getSelectionModel().getSelectedItem()); // print out the selected item
    }

    // method to return the selected album
    public String getSelectedAlbum() {
        return albumListView.getSelectionModel().getSelectedItem();
    }

    // method to delete the album
    public void deleteAlbum(String albumName) {
        System.out.println("Deleting album: " + albumName); // print out the album name
    }

    // return to the login screen (use the PhotoApp class)
    public void logout() {
    }

    // method to rename an existing album
    public void renameAlbum(String albumName, String newAlbumName) {
        user.getAlbum(albumName).setAlbumName(newAlbumName);
        obsList.set(albumListView.getSelectionModel().getSelectedIndex(), newAlbumName);
    }

    public void createAlbum(String albumName) {
        Album album = new Album(albumName);
        user.createAlbum(albumName);
        obsList.add(albumName);

    }

    // method to fix album names for directory modification
    public String fixAlbumName(String albumName) {
        // find the original album name (without the number of photos and range of dates)
        int index = albumName.indexOf("("); // find the index of the first parenthesis
        if (index != -1) { // if the index is not -1
            albumName = albumName.substring(0, index - 1); // get the substring from the beginning to the index - 1
        }
        return albumName;
    }


}
