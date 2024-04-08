package controller;

// Java imports
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// JavaFX imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Project imports
import model.Album;
import model.Photos;
import model.User;
import static model.Photos.errorAlert;
import static model.Photos.infoAlert;

/**
 * AlbumListController class is the controller for the album list view.
 * It allows the user to delete, rename, and create albums.
 * It also allows the user to select an album to view its photos.
 * It is used in the album list view.
 *
 * @author jacobjude
 * @author ks1686
 */
public class AlbumListController {
    @FXML ListView<String> albumListView;
    private ObservableList<String> obsList;
    private User user;
    private Photos app;


    /**
     * Method to start the album list controller
     * @param user: the user
     * @param app: the Photos app
     */
    public void start(User user, Photos app) {

        List<Album> albums = user.getAlbums();
        List<String> albumNames = new ArrayList<>();
        this.user = user;
        this.app = app;

        // add the number of photos on each album to the list and the range of dates
        for (Album album : albums) {

            String albumName = getAlbumName(album);
            albumNames.add(albumName);
        }
        obsList = FXCollections.observableArrayList(albumNames); // create an observable list from the list of albums
        albumListView.setItems(obsList); // set the list view to the observable list

        albumListView.getSelectionModel().select(0); // select the first item in the list

    }

    /**
     * Method to get the album name
     * @param album: the album
     * @return album name
     */
    private static String getAlbumName(Album album) {
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
        return albumName;
    }

    /**
     * Method to get the selected album
     * @return album name
     */
    public String getSelectedAlbum() {
        return albumListView.getSelectionModel().getSelectedItem();
    }

    /**
     * Method to delete an album
     * @param albumName: the album name
     */
    public void deleteAlbum(String albumName) {
        user.getAlbums().remove(user.getAlbum(albumName));
        obsList.remove(albumName);

        infoAlert("Album Deleted", "" ,"Album " + albumName + " has been deleted.");


        // load the homepage controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/homepage.fxml"));

        Pane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HomepageController homepageController = loader.getController();
        Stage stage = (Stage) albumListView.getScene().getWindow();
        homepageController.start(user, app);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method to rename an album
     * @param albumName: the album name
     * @param newAlbumName: the new album name
     */
    public void renameAlbum(String albumName, String newAlbumName) {
        // fix the album name
        albumName = fixAlbumName(albumName);

        // rename if new name isn't null, doesn't match the albumName, and doesn't already exist
        if (newAlbumName != null && !newAlbumName.equals(albumName) && !obsList.contains(newAlbumName)){
            user.getAlbum(albumName).setAlbumName(newAlbumName);
            obsList.set(albumListView.getSelectionModel().getSelectedIndex(), newAlbumName);

            // load the homepage controller
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/homepage.fxml"));

            Pane root;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            HomepageController homepageController = loader.getController();
            Stage stage = (Stage) albumListView.getScene().getWindow();
            homepageController.start(user, app);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
        } else {
            errorAlert("Error", "Invalid Album Name", "The album name is invalid.");
        }
    }

    /**
     * Method to create an album
     * @param albumName: the album name
     */
    public void createAlbum(String albumName) {
        // if the album name is not null and doesn't already exist, create the album
        if (albumName == null || albumName.isBlank()) {
            errorAlert("Error", "Invalid Album Name", "The album name is invalid. Make sure the name has non-whitespace characters.");
            return;
        }

        for (String name : obsList) {
            if (fixAlbumName(name).equals(albumName)) {
                errorAlert("Error", "Invalid Album Name", "The album name is invalid. The album already exists.");
                return;
            }
        }



        if (!obsList.contains(albumName)) {
            user.createAlbum(albumName);
            obsList.add(albumName);

            // load the homepage controller
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/homepage.fxml"));

            Pane root;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            HomepageController homepageController = loader.getController();
            Stage stage = (Stage) albumListView.getScene().getWindow();
            homepageController.start(user, app);
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();

        } else {
            errorAlert("Error", "Invalid Album Name", "The album name is invalid.");
        }

    }

    /**
     * Method to fix the album name
     * @param albumName: the album name
     * @return the fixed album name
     */
    public String fixAlbumName(String albumName) {
        // find the original album name (without the number of photos and range of dates)
        int index = albumName.indexOf("("); // find the index of the first parenthesis
        if (index != -1) { // if the index is not -1
            albumName = albumName.substring(0, index - 1); // get the substring from the beginning to the index - 1
        }
        return albumName;
    }


}
