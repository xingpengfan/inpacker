package inpacker.core.model;

public class InstagramPost {

    public final String username;
    public final String url;
    public final long createdTime;
    public final String type;
    public final String id;

    public InstagramPost(String username, String url, long createdTime, String type, String id) {
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

    public String extension() {
        return isVideo() ? ".mp4" : isImage() ? ".jpg" : "";
    }

}
