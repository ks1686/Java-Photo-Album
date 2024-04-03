package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

    
    public List<Photo> search(String query) {
        // query can be a tag or a Calendar date
        // check if the query is a calendar date
        List<Photo> result = new ArrayList<>();
        if (query.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // query is a date
            for (Photo photo : this.photos) {
                if (photo.getDate().toString().equals(query)) {
                    result.add(photo);
                }
            }
            return result;
        }

        // tags can look like "tagname=tagvalue" and can have conjunctions or disjuctions
        // ex. person=John AND location=New York
        // ex. person=John OR location=New York
        // no need to handle more than 1 conjunction or disjunction

        // split the query by " AND " or " OR "
        String[] parts = query.split(" AND | OR ");

        // if the query is a single tag
        if (parts.length == 1) {
            String[] tag = query.split("=");
            for (Photo photo : this.photos) {
                for (Map<String, String> currentTag : photo.getTags()) {
                    if (currentTag.containsKey(tag[0]) && currentTag.containsValue(tag[1])) {
                        result.add(photo);
                    }
                }
            }
            return result;
        } else if (parts.length == 2) {
            // if the query is a disjunction
            if (!query.contains(" AND ")) {
                String[] tag1 = parts[0].split("=");
                String[] tag2 = parts[1].split("=");
                for (Photo photo : this.photos) {
                    boolean found1 = false;
                    boolean found2 = false;
                    for (Map<String, String> tag : photo.getTags()) {
                        if (tag.containsKey(tag1[0]) && tag.containsValue(tag1[1])) {
                            found1 = true;
                        }
                        if (tag.containsKey(tag2[0]) && tag.containsValue(tag2[1])) {
                            found2 = true;
                        }
                    }
                    if (found1 && found2) {
                        result.add(photo);
                    }
                }
                return result;
            }
            
        } else {
            // if the query is a conjunction
            String[] tag1 = parts[0].split("=");
            String[] tag2 = parts[1].split("=");
            for (Photo photo : this.photos) {
                boolean found1 = false;
                boolean found2 = false;
                for (Map<String, String> tag : photo.getTags()) {
                    if (tag.containsKey(tag1[0]) && tag.containsValue(tag1[1])) {
                        found1 = true;
                    }
                    if (tag.containsKey(tag2[0]) && tag.containsValue(tag2[1])) {
                        found2 = true;
                    }
                }
                if (found1 || found2) {
                    result.add(photo);
                }
            }
            return result;
        }

        
        return result;  
    }
    
    
     
}