package app.dto;

import app.core.model.PackSettings;

import com.google.gson.annotations.SerializedName;

public class PackSettingsDto {

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("includeProfilePicture")
    public boolean includeProfilePicture;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public PackSettings getPackSettings() {
        return new PackSettings(includeImages, includeVideos, includeProfilePicture, getPattern());
    }

    private PackSettings.FileNamePattern getPattern() {
        switch (fileNamePattern) {
            case "id":
                return PackSettings.FileNamePattern.ID;
            case "index": default:
                return PackSettings.FileNamePattern.INDEX;
        }
    }
}
