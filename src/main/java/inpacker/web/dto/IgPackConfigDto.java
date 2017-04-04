package inpacker.web.dto;

import inpacker.instagram.IgPackConfig;

import com.google.gson.annotations.SerializedName;
import inpacker.instagram.IgPost;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

public class IgPackConfigDto {

    @SerializedName("username")
    public String username;

    @SerializedName("includeVideos")
    public boolean includeVideos;

    @SerializedName("includeImages")
    public boolean includeImages;

    @SerializedName("fileNamePattern")
    public String fileNamePattern;

    public IgPackConfig getIgPackConfig() {
        return new IgPackConfig(username, includeVideos, includeImages, 1500, getFileNameCreator());
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
