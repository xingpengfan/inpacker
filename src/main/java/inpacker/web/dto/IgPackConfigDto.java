package inpacker.web.dto;

import inpacker.core.model.IgPackConfig;

import com.google.gson.annotations.SerializedName;

public class IgPackConfigDto {

    @SerializedName("username")
    public String username;

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("includeProfilePicture")
    public boolean includeProfilePicture;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public IgPackConfig getIgPackConfig() {
        return new IgPackConfig(username, includeProfilePicture, includeVideos, includeImages, 1500);
    }
}
