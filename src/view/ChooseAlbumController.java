package view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.PhotoApp;
import model.User;

public class ChooseAlbumController {

    @FXML
    private Text titleText;

    @FXML protected Button selectAlbumButton;

    @FXML protected AlbumListController albumListController;

    private User user;
    private Album selectedAlbum;

    public Album getSelectedAlbum(){
        return selectedAlbum;
    }

    public void setTitleText(String text){
        titleText.setText(text);
    }

    public String getTitleText(){
        return titleText.getText();
    }

    public void setAlbum(Album album){
        selectedAlbum = album;
    }
    public void start(Stage stage, User user) {
        this.user = user;
        albumListController.start(stage, user);
    }

    public void selectAlbum(){
        String albumName = albumListController.getSelectedAlbum();
        for (Album album : user.getAlbums()) {
            if (album.getAlbumName().equals(albumName)) {
                selectedAlbum = album;
                break;
            }
        }
    }
   
}