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
import model.PhotoApp;


public class AlbumListController {
    @FXML ListView<String> albumListView;
    private ObservableList<String> obsList;

    private String username;

    // method to set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // method to start the album list controller
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

            // add the number of photos to the album name (messes up directory so be sure to have conditional to fix in other code)
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

                // add the range to the album name (messes up directory so be sure to have conditional to fix in other code)
                albums.set(i, albums.get(i) + " (" + range + ")"); // add the range to the album name
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

    // method to return the selected album
    public String getSelectedAlbum() {
        return albumListView.getSelectionModel().getSelectedItem();
    }

    // method to delete the album
    public void deleteAlbum(String albumName) {
        System.out.println("Deleting album: " + albumName); // print out the album name
        System.out.println("Username: " + username); // print out the username

        // find the original album name (without the number of photos and range of dates)
        albumName = fixAlbumName(albumName);

        // get the album directory
        File albumDir = new File("data/" + username + "/" + albumName);


        //print albumDir
        System.out.println("Album Directory: " + albumDir);

        // check if the album directory exists
        if (albumDir.exists()) {
            // delete all files in the album directory
            File[] files = albumDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!f.delete()) {
                        System.out.println("Failed to delete file: " + f.getName());
                    }
                }
            }

            // delete the album directory itself
            if (!albumDir.delete()) {
                System.out.println("Failed to delete album directory: " + albumDir.getName());
            }

            // refresh the list view
            start(new Stage(), username);
        } else {
            System.out.println("Album does not exist: " + albumName);
        }

    }

    // return to the login screen (use the PhotoApp class)
    public void logout() {
        // print out a message saying we are logging out of the user
        System.out.println("Logging out of user: " + username);

        // return to the login screen
        PhotoApp app = new PhotoApp();
        try {
            app.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // close the current stage
        albumListView.getScene().getWindow().hide();
    }

    // method to rename an existing album
    public void renameAlbum(String albumName, String newAlbumName) {
        // find the original album name (without the number of photos and range of dates)
        albumName = fixAlbumName(albumName);

        // get the album directory
        File albumDir = new File("data/" + username + "/" + albumName);

        // get the new album directory
        File newAlbumDir = new File("data/" + username + "/" + newAlbumName);

        // rename the album directory
        if (albumDir.renameTo(newAlbumDir)) {
            System.out.println("Album renamed to: " + newAlbumName);
        } else {
            System.out.println("Failed to rename album: " + albumName);
        }

        // refresh the list view
        start(new Stage(), username);
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
