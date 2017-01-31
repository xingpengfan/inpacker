package app.core;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("username")
    public String username;

    @SerializedName("is_private")
    public boolean isPrivate;

    @SerializedName("full_name")
    public String fullName;

    @SerializedName("biography")
    public String biography;

    @SerializedName("profile_pic_url_hd")
    public String profilePic;

    @SerializedName("count")
    public int count;

    @SerializedName("is_verified")
    public boolean isVerified;
}
