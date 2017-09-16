package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.px.PxUser;

import java.util.Objects;

public class PxUserDto {

    @SerializedName("id")
    String id;

    @SerializedName("username")
    String username;

    @SerializedName("firstname")
    String firstname;

    @SerializedName("lastname")
    String lastname;

    @SerializedName("city")
    String city;

    @SerializedName("userpic_url")
    String userpicUrl;

    @SerializedName("cover_url")
    String coverUrl;

    @SerializedName("photos_count")
    int photosCount;

    public PxUserDto(PxUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.city = user.getCity();
        this.userpicUrl = user.getUserpicUrl();
        this.coverUrl = user.getCoverUrl();
        this.photosCount = user.getPhotosCount();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PxUserDto)) {
            return false;
        }
        final PxUserDto that = (PxUserDto)obj;
        return photosCount == that.photosCount
               && Objects.equals(id, that.id)
               && Objects.equals(username, that.username)
               && Objects.equals(firstname, that.firstname)
               && Objects.equals(lastname, that.lastname)
               && Objects.equals(city, that.city)
               && Objects.equals(userpicUrl, that.userpicUrl)
               && Objects.equals(coverUrl, that.coverUrl);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(id);
        hash = 31 * hash + Objects.hashCode(username);
        hash = 31 * hash + Objects.hashCode(firstname);
        hash = 31 * hash + Objects.hashCode(lastname);
        hash = 31 * hash + Objects.hashCode(city);
        hash = 31 * hash + Objects.hashCode(userpicUrl);
        hash = 31 * hash + Objects.hashCode(coverUrl);
        hash = 31 * hash + photosCount;
        return hash;
    }
}
