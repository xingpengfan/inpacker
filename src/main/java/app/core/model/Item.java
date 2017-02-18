package app.core.model;

/**
 * InstagramUser media item: image or video
 */
public class Item {

    public final String username;
    public final String url;
    public final long createdTime;
    public final String type; // image or video
    public final String id;

    public Item(String username, String url, long createdTime, String type, String id) {
        this.username = username;
        this.url = url;
        this.createdTime = createdTime;
        this.type = type;
        this.id = id;
    }

    public boolean isVideo() {
        return "video".equals(type);
    }

    public boolean isImage() {
        return "image".equals(type);
    }
}
