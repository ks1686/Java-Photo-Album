package view;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

import model.Photos;


public class HomepageController {
    @FXML
    protected AlbumListController albumListController;

    @FXML protected TextField searchBarTextField;
    private Photos app;

    // private user object
    private User user;

    public void start(Stage stage, User user, Photos app) { // TODO: make a User object and pass that instead
        albumListController.start(stage, user, app);
        this.app = app;
        this.user = user;
    }

    // method to delete an album
    @FXML
    private void deleteAlbum() {
        String albumName = albumListController.getSelectedAlbum();

        // fix the album name
        albumName = albumListController.fixAlbumName(albumName);

        // if album is not null, delete the album
        if (albumName != null) {
            albumListController.deleteAlbum(albumName);
        }


    }

    // method to log out
    @FXML
    private void logout() {
        app.logout(app);
        // close the current window
        Stage stage = (Stage) albumListController.albumListView.getScene().getWindow();
        stage.close();
    }

    // method to rename an album
    // button press opens a dialog box to enter the new album name
    @FXML
    private void renameAlbum() {
        // open a text input dialog box
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename Album");
        dialog.setHeaderText("Enter the new album name:");
        dialog.setContentText("New album name:");

        // get the new album name
        String newAlbumName = null;
        try {
            newAlbumName = dialog.showAndWait().get();
        } catch (NoSuchElementException e) {
            return;
        }

        // get the selected album
        String albumName = albumListController.fixAlbumName(albumListController.getSelectedAlbum());

        // if the album name is not null, rename the album
        if (albumName != null && newAlbumName != null) {
            albumListController.renameAlbum(albumName, newAlbumName);
        } else {
            Photos.errorAlert("Error", "Invalid Album Name", "The album name is invalid.");
        }
    }

    @FXML public void createAlbum() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Album");
        dialog.setHeaderText("Enter the name of the new album:");
        dialog.setContentText("Album name:");

        String albumName = null;
        try {
            albumName = dialog.showAndWait().get();
        } catch (NoSuchElementException e) {
            return;
        }
        if (albumName != null) {
            albumListController.createAlbum(albumName);
        }
        
    }

    private boolean isValidSearchQuery(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }

        // query must be of the form MM/DD/YYYY-MM/DD/YYYY OR a tag in the form "key=value" OR a conjunction/disjunction of tags like "key=value AND key=value"m "key=value OR key=value"
        if (!query.matches("\\d{2}/\\d{2}/\\d{4}-\\d{2}/\\d{2}/\\d{4}") && !query.matches("\\w+=\\w+") && !query.matches("\\w+=\\w+ (AND|OR) \\w+=\\w+")) {
            return false;
        }
        return true;
    }

    @FXML public void searchPhotos() throws IOException {
        // string in the text bar
        String query = searchBarTextField.getText();
        if (!(isValidSearchQuery(query))){
            Photos.errorAlert("Invalid Search Query", "Invalid Search Query", "Invalid Search Query");
            return;
        }
        List<Photo> photos = user.searchAlbums(query);

        if (photos == null) {
            Photos.errorAlert("Search Error", "Search query is invalid. ", "Hover over search bar and see the tooltip for more information.");
            return;
        }
        
        

        String tempAlbumName = "Search Results";
        // go through the user's albums. if the album name is the same as the temp album name, keep appending a number to the end until it's unique
        int count = 1;
        String uniqueAlbumName = tempAlbumName;
        while (user.getAlbum(uniqueAlbumName) != null) {
            uniqueAlbumName = tempAlbumName + count;
            count++;
        }
        tempAlbumName = uniqueAlbumName;

        // create a new, temporary album to store the search results
        Album searchResults = new Album(uniqueAlbumName, photos);
        // load the gallery controller
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/searchresults.fxml"));
        Pane root = loader.load();
        SearchResultsController searchResultsController = loader.getController();

        // get the current stage
        Stage stage = (Stage) albumListController.albumListView.getScene().getWindow();
        // start the gallery controller
        
        
        // set the scene
        Scene scene = new Scene(root, 800, 600);
        searchResultsController.start(scene, app, user, searchResults);
        stage.setScene(scene);
        stage.show();


    }

    @FXML public void quit() {
        // quit application, but save it also
        app.quit();
    }

    @FXML public void openAlbum() throws IOException {
        // open the selected album
        String albumName = albumListController.getSelectedAlbum();
        albumName = albumListController.fixAlbumName(albumName);

        // get the album object
        Album album = user.getAlbum(albumName);

        // if the album is not null, open the album
        if (album != null) {
            // load the gallery controller
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/gallery.fxml"));
            Pane root = loader.load();
            GalleryController galleryController = loader.getController();

            // get the current stage
            Stage stage = (Stage) albumListController.albumListView.getScene().getWindow();
            // start the gallery controller
            
            galleryController.start(stage, app, album, user);
            // set the scene
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();
            return;
        } else {
            // show an error alert that the album could not be opened
            Photos.errorAlert("Open Album", "Failed to open album", "Failed to open album");
        }

    }

}
