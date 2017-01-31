package app.core;

import com.google.gson.annotations.SerializedName;

/**
 * User media item: image or video
 */
public class Item {

    @SerializedName("username")
    public String username;

    @SerializedName("url")
    public String url;

    @SerializedName("created_time")
    public long createdTime;

    @SerializedName("type")
    public String type; // image or video

    @SerializedName("id")
    public String id;

    @SerializedName("caption_text")
    public String captionText;

    public boolean isVideo() {
        return "video".equals(type);
    }

    public boolean isImage() {
        return "image".equals(type);
    }
}
