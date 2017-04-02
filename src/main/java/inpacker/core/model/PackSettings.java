package inpacker.core.model;

import java.time.Instant;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class PackSettings implements Predicate<InstagramPost>, BiFunction<InstagramPost, Integer, String> {

    @Override
    public String apply(InstagramPost post, Integer index) {
        if (post.id.contains("profile_picture"))
            return post.id + "." + post.extension();
        switch (fileNamePattern) {
            case INDEX:
                return index + post.extension(); // 1.jpg, 17.mp4
            case ID:
                return post.id + post.extension(); // 4154054931005.jpg, 23178444344138.mp4
            case DATE:
            default:
                return Instant.ofEpochSecond(post.createdTime) + post.extension(); // 2017-02-25T15:36:59Z.mp4
        }
    }

    @Override
    public boolean test(InstagramPost item) {
        return item.isImage() && includeImages || item.isVideo() && includeVideos;
    }

    public enum FileNamePattern {
        INDEX, ID, DATE
    }

    public final boolean         includeImages;
    public final boolean         includeVideos;
    public final boolean         includeProfilePicture;
    public final FileNamePattern fileNamePattern;

    public PackSettings(boolean images, boolean videos, boolean profilePicture, FileNamePattern pattern) {
        includeImages = images;
        includeVideos = videos;
        includeProfilePicture = profilePicture;
        fileNamePattern = pattern;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PackSettings)) {
            return false;
        }
        final PackSettings that = (PackSettings)obj;
        return includeImages == that.includeImages
               && includeVideos == that.includeVideos
               && includeProfilePicture == that.includeProfilePicture
               && Objects.equals(fileNamePattern, that.fileNamePattern);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Boolean.hashCode(includeImages);
        hash = 31 * hash + Boolean.hashCode(includeVideos);
        hash = 31 * hash + Boolean.hashCode(includeProfilePicture);
        hash = 31 * hash + Objects.hashCode(fileNamePattern);
        return hash;
    }
}
