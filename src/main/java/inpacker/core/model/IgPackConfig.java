package inpacker.core.model;

import java.util.Objects;
import java.util.function.Predicate;

public class IgPackConfig implements Predicate<InstagramPost> {

    public final String username;
    public final boolean includeProfilePicture;
    public final boolean includeVideos;
    public final boolean includeImages;
    public final int amount;

    public IgPackConfig(String username, boolean includeProfilePicture,
                        boolean includeVideos, boolean includeImages, int amount) {
        this.username = username;
        this.includeProfilePicture = includeProfilePicture;
        this.includeVideos = includeVideos;
        this.includeImages = includeImages;
        this.amount = amount;
    }

    @Override
    public boolean test(InstagramPost post) {
        return post.isImage() && includeImages || post.isVideo() && includeVideos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IgPackConfig)) {
            return false;
        }
        final IgPackConfig that = (IgPackConfig) obj;
        return includeProfilePicture == that.includeProfilePicture
                && includeVideos == that.includeVideos
                && includeImages == that.includeImages
                && amount == that.amount
                && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(username);
        hash = 31 * hash + Boolean.hashCode(includeProfilePicture);
        hash = 31 * hash + Boolean.hashCode(includeVideos);
        hash = 31 * hash + Boolean.hashCode(includeImages);
        hash = 31 * hash + amount;
        return hash;
    }
}
