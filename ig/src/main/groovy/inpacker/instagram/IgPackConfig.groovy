package inpacker.instagram

import groovy.transform.Immutable
import inpacker.core.PackConfig

import java.util.function.BiFunction

import static java.time.LocalDateTime.ofEpochSecond
import static java.time.ZoneOffset.*

@Immutable class IgPackConfig implements PackConfig<IgPackItem> {

    IgUser user
    boolean includeVideos, includeImages
    int size // number of items to be added to the pack
    String fileNamePattern

    BiFunction<Integer, IgPost, String> getFileNameCreator() {
        switch (fileNamePattern) {
            case 'id':
                return { idx, post -> post.id + post.extension() }
            case 'index':
            case 'idx':
                return { idx, post -> idx + post.extension() }
            case 'timestamp':
                return { idx, post -> post.createdTime + post.extension() }
            case 'date':
            default:
                return { idx, post -> ofEpochSecond(post.getCreatedTime(), 0, UTC).toString() + post.extension() }
        }
    }

    @Override
    boolean test(IgPackItem packItem) {
        final IgPost post = packItem.getPost()
        post.isImage() && includeImages || post.isVideo() && includeVideos
    }

    @Override
    String getUniqueId() {
        user.username + "_" + hashCode()
    }

    @Override
    int numberOfItems() {
        includeImages && includeVideos ? size : -1
    }
}
