package app.core.model;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class PackSettings implements Predicate<Item>, BiFunction<Item, Integer, String> {

    @Override
    public String apply(Item item, Integer index) {
        switch (fileNamePattern) {
            case INDEX:
                return index + fileNameExtension(item);
            case ID:
            default:
                return item.id + fileNameExtension(item);
        }
    }

    @Override
    public boolean test(Item item) {
        return item.isImage() && includeImages || item.isVideo() && includeVideos;
    }

    public enum FileNamePattern {
        INDEX, ID
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

    private String fileNameExtension(Item item) {
        return item.isVideo() ? ".mp4" : item.isImage() ? ".jpg" : "";
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
