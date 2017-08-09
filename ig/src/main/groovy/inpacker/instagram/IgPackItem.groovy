package inpacker.instagram

import inpacker.core.PackItem

import java.util.function.BiFunction

class IgPackItem implements PackItem {

    private final IgPost post
    private final int index
    private final BiFunction<Integer, IgPost, String> fileNameCreator

    IgPackItem(IgPost post, int index, BiFunction<Integer, IgPost, String> fileNameCreator) {
        this.post = post
        this.index = index
        this.fileNameCreator = fileNameCreator
    }

    IgPost getPost() {
        return post
    }

    @Override
    String getUrl() {
        return post.url
    }

    @Override
    String getFileName() {
        return fileNameCreator.apply(index, post)
    }
}
