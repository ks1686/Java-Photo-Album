package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import controller.LoginController;
import javafx.scene.control.Alert;

// TODO: remove all print statements before submitting

public class Photos extends Application implements Serializable {

    public static final String storeDir = "data";
    public static final String storeFile = "data.dat";
    static final long serialVersionUID = 1L;

    private List<User> userList;

    public List<User> getUsers() {
        return userList;
    }

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
        primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                
                try {
                    Photos.writeApp(finalApp);
                } catch (IOException e) {
                    errorAlert("Error writing to file", "", "Error writing to file /data/data.dat");
                }
            }
        });
        
        primaryStage.show();
        
    }

    public static void writeApp(Photos app) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(app);
        oos.close();
    }

    public static Photos readApp() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        Photos app = (Photos) ois.readObject();
        ois.close();
        return app;
    }


    public static void main(String[] args) throws Exception {
        launch(args);
    }

    // method to create an error alert
    public static void errorAlert(String title, String header, String content) {
        // create an alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // method to create an information alert
    public static void infoAlert(String title, String header, String content) {
        // create an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // logout method (do NOT create a new PhotoApp object)
    public void logout(Photos app) {
        // save the app
        try {
            Photos.writeApp(app);
        } catch (Exception e) {
            // show an alert that there was an error writing to the file
            errorAlert("Error writing to file", "", "Error writing to file /data/data.dat");
        }

        // print out a message saying we are logging out of the admin
        System.out.println("Logging out of current user");
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
            e.printStackTrace();
            // show an error alert that the login screen could not be returned to
            errorAlert("Logout", "Failed to return to login screen", "Failed to return to login screen");
        }
    }

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