package app.core.model;

import java.time.Instant;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class PackSettings implements Predicate<Item>, BiFunction<Item, Integer, String> {

    /**
     * Consumes an item and its index and creates a file name for the specified item.
     * File name depends on the fileNamePattern field of the PackSettings instance this method is called on
     *
     * @param item item to be named
     * @param index fetch index of the specified item; index of the newest user's item is 1
     * @return file name with file extension e.g. 13.mp4
     */
    @Override
    public String apply(Item item, Integer index) {
        if (item.id.contains("profile_picture"))
            return item.id + "." + item.extension();
        switch (fileNamePattern) {
            case INDEX:
                return index + "." + item.extension(); // 1.jpg, 17.mp4
            case ID:
                return item.id + "." + item.extension(); // 4154054931005.jpg, 23178444344138.mp4
            case DATE:
            default:
                return Instant.ofEpochSecond(item.createdTime) + "." + item.extension(); // 2017-02-25T15:36:59Z.mp4
        }
    }

    /**
     * Tests whether the specified item fits properties declared
     * in the PackSettings instance this method is called on.
     *
     * @param item item to be tested
     * @return {@code true} if the specified item is suitable, otherwise {@code false}
     */
    @Override
    public boolean test(Item item) {
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
