package app.core.model;

import java.util.Objects;

public class PackSettings {

    public enum FileNamePattern {
        INDEX, ID
    }

    public final boolean         includeImages;
    public final boolean         includeVideos;
    public final FileNamePattern fileNamePattern;

    public PackSettings(boolean images, boolean videos, FileNamePattern pattern) {
        includeImages = images;
        includeVideos = videos;
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
               && Objects.equals(fileNamePattern, that.fileNamePattern);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Boolean.hashCode(includeImages);
        hash = 31 * hash + Boolean.hashCode(includeVideos);
        hash = 31 * hash + Objects.hashCode(fileNamePattern);
        return hash;
    }
}
