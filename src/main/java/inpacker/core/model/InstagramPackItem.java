package inpacker.core.model;

import inpacker.core.PackItem;

public class InstagramPackItem implements PackItem {

    private final InstagramPost post;

    public InstagramPackItem(InstagramPost instagramPost) {
        post = instagramPost;
    }

    public InstagramPost getPost() {
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
