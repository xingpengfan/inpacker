package inpacker.web.dto;

import inpacker.instagram.IgPackConfig;

import com.google.gson.annotations.SerializedName;
import inpacker.instagram.IgPost;
import inpacker.instagram.IgUser;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

public class CreatePackRequest {

    @SerializedName("username")
    public String username;

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public CreatePackRequest() {}

    public CreatePackRequest(String username, boolean includeVideos,
                             boolean includeImages, String fileNamePattern) {
        this.username = username;
        this.includeVideos = includeVideos;
        this.includeImages = includeImages;
        this.fileNamePattern = fileNamePattern;
    }

    public IgPackConfig toIgPackConfig(IgUser user) {
        return new IgPackConfig(user, includeVideos, includeImages, getFileNameCreator());
    }

    private BiFunction<Integer, IgPost, String> getFileNameCreator() {
        switch (fileNamePattern) {
            case "id":
                return (idx, post) -> post.id + post.extension();
            case "date": default:
                return (idx, post) -> LocalDateTime.ofEpochSecond(post.createdTime, 0, ZoneOffset.UTC) + post.extension();
            case "index":
                return (idx, post) -> idx + post.extension();
        }
    }
}
