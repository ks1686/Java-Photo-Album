package model;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.nio.file.Files; // idk if we can use this, just found this on google
import java.nio.file.Path;

public class Photo {

    private String filepath;
    private String caption;
    private Calendar date;
    private Map<String, Set<String>> tags;

    public Photo(String filepath, String caption, Map<String, Set<String>> tags) throws NullPointerException, IllegalArgumentException {
        Path path = Path.of(filepath);

        if (!Files.exists(path)) {
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

        if (date == null) {
            throw new NullPointerException("date cannot be null");
        }

        if (tags == null) {
            this.tags = new HashMap<>();
        } else {
            this.tags = tags;
        }

    
        this.date = Calendar.getInstance();
        this.date.setTimeInMillis(path.toFile().lastModified());

        this.filepath = filepath;
        this.caption = caption;

    
    }

    public Photo(String filepath) {
        this(filepath, "", new HashMap<>());
    }

    public Photo(String filepath, String caption) {
        this(filepath, caption, new HashMap<>());
    }

    public Photo(String filepath, Map<String, Set<String>> tags) {
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

    public Calendar getDate() {
        return date;
    }

    public Map<String, Set<String>> getTags() {
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

        // find the set
        Set<String> tagSet = tags.get(key);
        if (tagSet == null) {
            tagSet = new HashSet<>();
            tags.put(key, tagSet);
        } else {
            tagSet.add(value);
        }

    }

    public void removeTag(String key, String value) throws NullPointerException {
        // find the set
        Set<String> tagSet = tags.get(key);
        if (tagSet == null) {
            throw new NullPointerException("key does not exist");
        } else {
            tagSet.remove(value);
        }

        // if the set is empty, remove the key
        if (tagSet.isEmpty()) {
            tags.remove(key);
        }

    }

    public String toString() {
        return String.format("Photo: %s || Caption: %s || Date: %s || Tags: %s", filepath, caption, date, tags.toString());
    }
}
