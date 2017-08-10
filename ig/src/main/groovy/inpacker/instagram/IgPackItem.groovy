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
        post
    }

    @Override
    String getUrl() {
        post.url
    }

    @Override
    String getFileName() {
        fileNameCreator.apply(index, post)
    }
}
