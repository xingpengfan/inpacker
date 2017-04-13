package inpacker.instagram;

import inpacker.core.PackConfig;

import java.util.Objects;
import java.util.function.BiFunction;

import static java.time.LocalDateTime.ofEpochSecond;
import static java.time.ZoneOffset.*;

public class IgPackConfig implements PackConfig<IgPackItem> {

    public final IgUser user;
    public final boolean includeVideos;
    public final boolean includeImages;
    public final int amount;
    public final String fileNamePattern;

    private static final String defaultFileNamePattern = "date";

    public IgPackConfig(IgUser user, boolean includeVideos, boolean includeImages,
                        int amount, String fileNamePattern) {
        this.user = user;
        this.includeVideos = includeVideos;
        this.includeImages = includeImages;
        amount = Math.min(Math.min(amount, user.count), Constants.MAX_ITEMS_PER_PACK);
        this.amount = amount;
        this.fileNamePattern = fileNamePattern == null ? defaultFileNamePattern : fileNamePattern;
    }

    public IgPackConfig(IgUser user, boolean includeVideos, boolean includeImages, String fileNamePattern) {
        this(user, includeVideos, includeImages, Constants.MAX_ITEMS_PER_PACK, fileNamePattern);
    }

    public BiFunction<Integer, IgPost, String> getFileNameCreator() {
        switch (fileNamePattern) {
            case "id":
                return (idx, post) -> post.id + post.extension();
            case "index":
            case "idx":
                return (idx, post) -> idx + post.extension();
            case "date":
            default:
                return (idx, post) -> ofEpochSecond(post.createdTime, 0, UTC) + post.extension();
        }
    }

    @Override
    public boolean test(IgPackItem packItem) {
        final IgPost post = packItem.getPost();
        return post.isImage() && includeImages || post.isVideo() && includeVideos;
    }

    @Override
    public String getUniqueId() {
        return user.username + "_" + hashCode();
    }

    @Override
    public int numberOfItems() {
        if (includeImages && includeVideos)
            return amount;
        return -1;
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
        return includeVideos == that.includeVideos
                && includeImages == that.includeImages
                && amount == that.amount
                && Objects.equals(user, that.user)
                && Objects.equals(fileNamePattern, that.fileNamePattern);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(user);
        hash = 31 * hash + Boolean.hashCode(includeVideos);
        hash = 31 * hash + Boolean.hashCode(includeImages);
        hash = 31 * hash + amount;
        hash = 31 * hash + Objects.hashCode(fileNamePattern);
        return hash;
    }
}
