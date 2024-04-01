package model;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Album> albums; // ArrayList of albums
    
    public User(String username) throws NullPointerException, IllegalArgumentException {
        if (username == null) {
            throw new NullPointerException("username cannot be null");
        } else if (username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be empty");
        } 

        this.username = username;
        this.albums = new ArrayList<>(); // Initialize the ArrayList
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws NullPointerException, IllegalArgumentException {
        if (username == null) {
            throw new NullPointerException("username cannot be null");
        } else if (username.isEmpty()) {
            throw new IllegalArgumentException("username cannot be empty");
        }
        this.username = username;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void createAlbum(String albumName) {
        Album album = new Album(albumName);
        albums.add(album);  // TODO: make sure duplicates are not added
    }

    public void deleteAlbum(String albumName) {
        for (int i = 0; i < albums.size(); i++) {
            if (albums.get(i).getAlbumName().equals(albumName)) {
                albums.remove(i); // Remove the album from the ArrayList
                break;
            }
        }
    }

    public Album getAlbum(String albumName) {
        for (int i = 0; i < albums.size(); i++) {
            if (albums.get(i).getAlbumName().equals(albumName)) {
                return albums.get(i); // Return the album
            }
        }
        return null; // Return null if the album is not found
    }

    public String toString() {
        String result = "";
        for (Album album : albums) {
            result += album.toString() + "\n";
        }

        return "User: " + username + "\n" + result;
    }


}
