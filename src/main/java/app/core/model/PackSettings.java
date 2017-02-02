package app.core.model;

import com.google.gson.annotations.SerializedName;

public class PackSettings {

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;
}
