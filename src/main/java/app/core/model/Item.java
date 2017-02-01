package app.core.model;

import com.google.gson.annotations.SerializedName;

/**
 * User media item: image or video
 */
public class Item {

    @SerializedName("username")
    public String username;

    @SerializedName("url")
    public String url;

    @SerializedName("createdTime")
    public long createdTime;

    @SerializedName("type")
    public String type; // image or video

    @SerializedName("id")
    public String id;

    public boolean isVideo() {
        return "video".equals(type);
    }

    public boolean isImage() {
        return "image".equals(type);
    }
}
