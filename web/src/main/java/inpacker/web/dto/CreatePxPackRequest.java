package inpacker.web.dto;

import inpacker.px.PxPackConfig;
import inpacker.px.PxUser;

import com.google.gson.annotations.SerializedName;

public class CreatePxPackRequest {

    @SerializedName("username")
    public String username;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public CreatePxPackRequest(String username, String fileNamePattern) {
        this.username = username;
        this.fileNamePattern = fileNamePattern;
    }

    public PxPackConfig config(PxUser user) {
        return new PxPackConfig(user, user.getPhotosCount(), fileNamePattern);
    }
}
