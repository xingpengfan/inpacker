package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgUser;

public class CreatePackRequest {

    @SerializedName("username")
    public String username;

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public CreatePackRequest(String username, boolean includeVideos,
                             boolean includeImages, String fileNamePattern) {
        this.username = username;
        this.includeVideos = includeVideos;
        this.includeImages = includeImages;
        this.fileNamePattern = fileNamePattern;
    }

    public IgPackConfig toIgPackConfig(IgUser user) {
        return new IgPackConfig(user, includeVideos, includeImages, fileNamePattern);
    }
}
