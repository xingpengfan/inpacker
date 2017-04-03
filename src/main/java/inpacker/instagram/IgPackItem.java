package inpacker.instagram;

import inpacker.core.PackItem;

public class IgPackItem implements PackItem {

    private final IgPost post;

    public IgPackItem(IgPost post) {
        this.post = post;
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
        return post.id + post.extension();
    }
}
