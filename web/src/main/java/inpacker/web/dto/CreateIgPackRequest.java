package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgUser;

import static java.lang.Math.min;

public class CreateIgPackRequest {

    @SerializedName("username")
    public String username;

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    private static final int MAX_ITEMS_PER_PACK = 2000;

    public CreateIgPackRequest(String username, boolean includeVideos, boolean includeImages, String fileNamePattern) {
        this.username = username;
        this.includeVideos = includeVideos;
        this.includeImages = includeImages;
        this.fileNamePattern = fileNamePattern;
    }

    public IgPackConfig toIgPackConfig(IgUser user) {
        return new IgPackConfig(user, includeVideos, includeImages, min(MAX_ITEMS_PER_PACK, user.getCount()), fileNamePattern);
    }
}
