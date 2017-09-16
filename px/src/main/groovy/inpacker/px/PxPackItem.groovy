package inpacker.px

import inpacker.core.PackItem

import java.util.function.BiFunction

class PxPackItem implements PackItem {

    private final PxPost post
    private final int index
    private final BiFunction<Integer, PxPost, String> fileNameCreator

    PxPackItem(PxPost post, int index, BiFunction<Integer, PxPost, String> fileNameCreator) {
        this.post = post
        this.index = index
        this.fileNameCreator = fileNameCreator
    }

    PxPost getPost() {
        post
    }

    @Override
    String getUrl() {
        post.getUrl()
    }

    @Override
    String getFileName() {
        fileNameCreator.apply(index, post)
    }
}
