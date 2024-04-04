package view;

import javafx.fxml.FXML;
import javafx.stage.Stage;


public class HomepageController {
    @FXML
    protected AlbumListController albumListController;

    public void start(Stage stage, String username) {
        albumListController.start(stage, username);
        // set the username for the album list controller
        albumListController.setUsername(username);
    }

    @FXML
    private void deleteAlbum() {
        String albumName = albumListController.getSelectedAlbum();

        // if album is not null, delete the album
        if (albumName != null) {
            albumListController.deleteAlbum(albumName);
        }
    }

}
