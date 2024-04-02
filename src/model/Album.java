package model;

import java.util.ArrayList;
import java.util.List;


public class Album {
    private String albumName;
    private List<Photo> photos;

    public Album(String albumName) throws NullPointerException, IllegalArgumentException {
        this(albumName, new ArrayList<>());
    }
    
    public Album(String albumName, List<Photo> photos) throws NullPointerException, IllegalArgumentException {
        if (albumName == null) {
            throw new NullPointerException("albumName cannot be null");
        } else if (albumName.isEmpty()) {
            throw new IllegalArgumentException("albumName cannot be empty");
        }
        if (photos == null) {
            throw new NullPointerException("photos cannot be null");
        }
        
        this.albumName = albumName;
    }

    public void addPhoto(Photo photo) {
        this.photos.add(photo); // may need to catch an exception here?
    }

    public void addPhoto(String filepath) {
        this.photos.add(new Photo(filepath)); // may need to catch an exception here?
    }

    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
    }

    public List<Photo> getPhotos() {
        return new ArrayList<>(this.photos);
    }

    public String getAlbumName() {
        return albumName;
    }
    
    public int getSize() {
        return this.photos.size();
    }

    public void setAlbumName(String albumName) throws NullPointerException, IllegalArgumentException {
        if (albumName == null) {
            throw new NullPointerException("albumName cannot be null");
        } else if (albumName.isEmpty()) {
            throw new IllegalArgumentException("albumName cannot be empty");
        }
        this.albumName = albumName;
    }

    public static void copyPhotoTo(Photo photo, Album album) throws NullPointerException, IllegalArgumentException {
        if (photo == null) {
            throw new NullPointerException("photo cannot be null");
        } else if (album == null) {
            throw new NullPointerException("album cannot be null");
        }
        album.addPhoto(photo);
    }

    public void movePhotoTo(Photo photo, Album album) throws NullPointerException, IllegalArgumentException {
        if (photo == null) {
            throw new NullPointerException("photo cannot be null");
        } else if (album == null) {
            throw new NullPointerException("album cannot be null");
        } else if (!this.photos.contains(photo)) {
            throw new IllegalArgumentException("photo does not exist in this album");
        }

        album.addPhoto(photo);
        this.photos.remove(photo);
    }

    public String toString() {
        // get the toString of all the photos in the album and album name
        String result = "";
        for (Photo photo : this.photos) {
            result += photo.toString() + "\n";
        }
        return "Album: " + this.albumName + "\nPhotos:\n" + result;
    }

    /*
    public List<Photo> search(query) {
        return null; // TODO: Implement search (for date and tags)
    }
    
     */
}