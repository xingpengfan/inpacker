package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.instagram.IgUser;

import java.util.Objects;

public class IgUserDto {

    @SerializedName("instagramId")
    String instagramId;

    @SerializedName("username")
    String username;

    @SerializedName("isPrivate")
    boolean isPrivate;

    @SerializedName("fullName")
    String fullName;

    @SerializedName("biography")
    String biography;

    @SerializedName("profilePic")
    String profilePic;

    @SerializedName("count")
    int count;

    @SerializedName("isVerified")
    boolean isVerified;

    public IgUserDto(IgUser user) {
        instagramId = user.getInstagramId();
        username = user.getUsername();
        isPrivate = user.getIsPrivate();
        fullName = user.getFullName();
        biography = user.getBiography();
        profilePic = user.getProfilePic();
        count = user.getCount();
        isVerified = user.getIsVerified();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IgUserDto)) {
            return false;
        }
        final IgUserDto that = (IgUserDto) obj;
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
