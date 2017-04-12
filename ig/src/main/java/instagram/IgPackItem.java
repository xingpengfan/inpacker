package instagram;

import core.PackItem;

import java.util.function.BiFunction;

public class IgPackItem implements PackItem {

    private final IgPost post;
    private final int index;
    private final BiFunction<Integer, IgPost, String> fileNameCreator;

    public IgPackItem(IgPost post, int index, BiFunction<Integer, IgPost, String> fileNameCreator) {
        this.post = post;
        this.index = index;
        this.fileNameCreator = fileNameCreator;
    }

    public IgPost getPost() {
        return post;
    }

    @Override
    public String getUrl() {
        return post.url;
    }

    @Override
    public String getFileName() {
        return fileNameCreator.apply(index, post);
    }
}
