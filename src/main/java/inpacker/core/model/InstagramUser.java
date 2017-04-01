package inpacker.core.model;

import com.google.gson.annotations.SerializedName;

public class InstagramUser {

    @SerializedName("instagramId")
    public String instagramId;

    @SerializedName("username")
    public String username;

    @SerializedName("isPrivate")
    public boolean isPrivate;

    @SerializedName("fullName")
    public String fullName;

    @SerializedName("biography")
    public String biography;

    @SerializedName("profilePic")
    public String profilePic;

    @SerializedName("count")
    public int count;

    @SerializedName("isVerified")
    public boolean isVerified;
}
