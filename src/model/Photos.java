package model;

// Java imports
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// JavaFX imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

// Project imports
import controller.LoginController;

/**
 *  Represents the application. The application has a list of users. The application can be created with a list of users.
 *  The application can have users added to it. The application can have users retrieved from it.
 *  The application can be saved to a file and read from a file.
 *  The application can create a stock user with stock photos.
 *  The application can start the application.
 *  The application can create an error alert.
 *  The application can create an information alert.
 *  The application can logout.
 *  The application can quit.
 *
 * @author jacobjude
 * @author ks1686
 */
public class Photos extends Application implements Serializable {

    public static final String storeDir = "data";
    public static final String storeFile = "data.dat";
    @Serial
    private static final long serialVersionUID = 1L;

    private List<User> userList;

    /**
     * gets the list of users
     *
     * @return the list of users
     */
    public List<User> getUsers() {
        return userList;
    }

    /**
     * create stock user with stock photos
     *
     * @param app the Photos object
     */
    private void createStockUser(Photos app) {
        // get all the files in data/users/stock/photos/
        File stockPhotos = new File("data/users/stock/photos");
        File[] photos = stockPhotos.listFiles();

        
        User stockUser = new User("stock");
        stockUser.createAlbum("stock");
        for (File photo : photos) {
            stockUser.getAlbum("stock").addPhoto(photo.getAbsolutePath());
        }
        app.userList.add(stockUser);
    }

    /**
     * start the application
     *
     * @param primaryStage the stage
     * @throws Exception if there is an exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Photos app;
        try {
            app = Photos.readApp();
        } catch (Exception e) {
            // if there's no data.dat file made, create a new PhotoApp and create stock user
            app = new Photos();
            app.userList = new ArrayList<>();
            createStockUser(app);
        }
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setApp(app);

        primaryStage.setTitle("Photo Album");
        primaryStage.setScene(new Scene(root, 800, 600));
        final Photos finalApp = app; // doesn't work without this for some reason

        // if user presses the X button, save the state of the app (this will save all Users, Albums, Photo objects)
        primaryStage.setOnCloseRequest(event -> {

            try {
                Photos.writeApp(finalApp);
            } catch (IOException e) {
                errorAlert("Error writing to file", "", "Error writing to file /data/data.dat");
            }
        });
        
        primaryStage.show();
        
    }

    /**
     * writes the app to a file
     * @param app: the app to write
     * @throws IOException: if there is an error writing the file
     */
    public static void writeApp(Photos app) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(app);
        oos.close();
    }

    /**
     * reads the app from a file
     * @return the app
     * @throws IOException: if there is an error reading the file
     * @throws ClassNotFoundException: if the class is not found
     */
    public static Photos readApp() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        Photos app = (Photos) ois.readObject();
        ois.close();
        return app;
    }


    /**
     * launches the application
     * @param args: the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * creates an error alert
     * @param title: the title of the alert
     * @param header: the header of the alert
     * @param content: the content of the alert
     */
    public static void errorAlert(String title, String header, String content) {
        // create an alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * creates an information alert
     * @param title: the title of the alert
     * @param header: the header of the alert
     * @param content: the content of the alert
     */
    public static void infoAlert(String title, String header, String content) {
        // create an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * log out of the application
     * @param app: the app to log out of
     */
    public void logout(Photos app) {
        // save the app
        try {
            Photos.writeApp(app);
        } catch (Exception e) {
            // show an alert that there was an error writing to the file
            errorAlert("Error writing to file", "", "Error writing to file /data/data.dat");
        }

        // load the logincontroller.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        try {
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.setApp(this);
            Stage stage = new Stage();
            stage.setTitle("Photo Album");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

        } catch (IOException e) {
            // show an error alert that the login screen could not be returned to
            errorAlert("Logout", "Failed to return to login screen", "Failed to return to login screen");
        }
    }

    /**
     * quit the application
     */
    public void quit(){ 
        // save the app 
        // TODO: test and make sure this actually saves the data
        try {
            Photos.writeApp(this);
        } catch (Exception e) {
            // show an alert that there was an error writing to the file
            errorAlert("Error writing to file", "", "Error writing to file /data/data.dat");
        }
        System.exit(0);
    }
}