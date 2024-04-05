package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.io.File;


public class Photo implements Serializable {

    private String filepath;
    private String caption;
    private Calendar date;
    private List<Map<String, String>> tags;

    public Photo(String filepath, String caption, List<Map<String, String>> tags) throws NullPointerException, IllegalArgumentException {
        File file = new File(filepath);

        if (!file.exists()) {
            throw new IllegalArgumentException("File does not exist");
        }

        if (filepath == null) {
            throw new NullPointerException("filepath cannot be null");
        } else if (filepath.isEmpty()) {
            throw new IllegalArgumentException("filepath cannot be empty");
        }

        if (!(filepath.endsWith(".bmp") || filepath.endsWith(".gif") || filepath.endsWith(".jpeg") || filepath.endsWith(".png"))) {
            throw new IllegalArgumentException("File must be a BMP, GIF, JPEG, or PNG file");
        }

        if (caption == null) {
            throw new NullPointerException("caption cannot be null");
        }

        if (tags == null) {
            this.tags = new ArrayList<>();
        } else {
            this.tags = tags;
        }

        this.date = Calendar.getInstance(); // get the current date and time
        this.date.setTimeInMillis(file.lastModified()); // set the date and time to the last modified date of the file

        this.filepath = filepath;
        this.caption = caption;
    }

    public Photo(String filepath) {
        this(filepath, "", new ArrayList<>());
    }

    public Photo(String filepath, String caption) {
        this(filepath, caption, new ArrayList<>());
    }

    public Photo(String filepath, List<Map<String, String>> tags) {
        this(filepath, "", tags);
    }

    public String getFilePath() {
        return filepath;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) throws NullPointerException {
        if (caption == null) {
            throw new NullPointerException("caption cannot be null");
        }
        this.caption = caption;
    }

    // get the date of the photo
    public Calendar getDate() {
        return date;
    }

    public List<Map<String, String>> getTags() {
        return tags;
    }

    public void addTag(String key, String value) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        } else if (key.isEmpty()) {
            throw new IllegalArgumentException("key cannot be empty");
        }

        if (value == null) {
            throw new NullPointerException("value cannot be null");
        } else if (value.isEmpty()) {
            throw new IllegalArgumentException("value cannot be empty");
        }

        Map<String, String> tag = Map.of(key, value);
        tags.add(tag);
    }

    public void removeTag(String key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        } else if (key.isEmpty()) {
            throw new IllegalArgumentException("key cannot be empty");
        }

        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).containsKey(key)) {
                tags.remove(i);
                break;
            }
        }
    }


    public String toString() {
        return String.format("Photo: %s || Caption: %s || Date: %s || Tags: %s", filepath, caption, date, tags.toString());
    }
}
