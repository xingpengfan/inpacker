package instagram;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class IgUser {

    @SerializedName("instagramId")
    public final String instagramId;

    @SerializedName("username")
    public final String username;

    @SerializedName("isPrivate")
    public final boolean isPrivate;

    @SerializedName("fullName")
    public final String fullName;

    @SerializedName("biography")
    public final String biography;

    @SerializedName("profilePic")
    public final String profilePic;

    @SerializedName("count")
    public final int count;

    @SerializedName("isVerified")
    public final boolean isVerified;

    public IgUser(String instagramId, String username, boolean isPrivate, String fullName,
                  String biography, String profilePic, int count, boolean isVerified) {
        this.instagramId = instagramId;
        this.username = username;
        this.isPrivate = isPrivate;
        this.fullName = fullName;
        this.biography = biography;
        this.profilePic = profilePic;
        this.count = count;
        this.isVerified = isVerified;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IgUser)) {
            return false;
        }
        final IgUser that = (IgUser) obj;
        return isPrivate == that.isPrivate
                && count == that.count
                && isVerified == that.isVerified
                && Objects.equals(instagramId, that.instagramId)
                && Objects.equals(username, that.username)
                && Objects.equals(fullName, that.fullName)
                && Objects.equals(biography, that.biography)
                && Objects.equals(profilePic, that.profilePic);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(instagramId);
        hash = 31 * hash + Objects.hashCode(username);
        hash = 31 * hash + Boolean.hashCode(isPrivate);
        hash = 31 * hash + Objects.hashCode(fullName);
        hash = 31 * hash + Objects.hashCode(biography);
        hash = 31 * hash + Objects.hashCode(profilePic);
        hash = 31 * hash + count;
        hash = 31 * hash + Boolean.hashCode(isVerified);
        return hash;
    }
}
