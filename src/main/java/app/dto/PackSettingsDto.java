package app.dto;

import app.core.model.PackSettings;

import com.google.gson.annotations.SerializedName;

public class PackSettingsDto {

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public PackSettings getPackSettings() {
        return new PackSettings(includeImages, includeVideos, getPattern());
    }

    private PackSettings.FileNamePattern getPattern() {
        switch (fileNamePattern) {
            case "index":
                return PackSettings.FileNamePattern.INDEX;
            default:
                return PackSettings.FileNamePattern.ID;
        }
    }
}
