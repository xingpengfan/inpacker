package inpacker.instagram;

import inpacker.core.PackConfig;

import java.util.Objects;
import java.util.function.BiFunction;

public class IgPackConfig implements PackConfig<IgPackItem> {

    public final String username;
    public final boolean includeVideos;
    public final boolean includeImages;
    public final int amount;
    public final BiFunction<Integer, IgPost, String> fileNameCreator;

    public IgPackConfig(String username, boolean includeVideos, boolean includeImages,
                        int amount, BiFunction<Integer, IgPost, String> itemFilenameCreator) {
        this.username = username;
        this.includeVideos = includeVideos;
        this.includeImages = includeImages;
        this.amount = amount;
        this.fileNameCreator = itemFilenameCreator;
    }

    @Override
    public boolean test(IgPackItem packItem) {
        final IgPost post = packItem.getPost();
        return post.isImage() && includeImages || post.isVideo() && includeVideos;
    }

    @Override
    public String getPackName() {
        return username + "_" + hashCode();
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
                && Objects.equals(username, that.username)
                && Objects.equals(fileNameCreator, that.fileNameCreator);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(username);
        hash = 31 * hash + Boolean.hashCode(includeVideos);
        hash = 31 * hash + Boolean.hashCode(includeImages);
        hash = 31 * hash + amount;
        hash = 31 * hash + Objects.hashCode(fileNameCreator);
        return hash;
    }
}
