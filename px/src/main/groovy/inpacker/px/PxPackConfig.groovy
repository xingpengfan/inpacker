package inpacker.px

import groovy.transform.Immutable
import inpacker.core.PackConfig

import java.util.function.BiFunction

import static java.lang.Math.min

@Immutable class PxPackConfig implements PackConfig<PxPackItem> {

    PxUser user
    int size
    String fileNamePattern

    BiFunction<Integer, PxPost, String> getFileNameCreator() {
        switch (fileNamePattern) {
            case 'id':
                return { idx, post -> post.id + post.extension() }
            case 'name':
                return { idx, post -> post.name + '_' + post.id + post.extension() }
            case 'index':
            case 'idx':
            default:
                return { idx, post -> idx + post.extension() }
        }
    }

    @Override
    boolean test(PxPackItem item) {
        true
    }

    @Override
    String getUniqueId() {
        user.username + '_' + hashCode()
    }

    @Override
    int numberOfItems() {
        min(user.getPhotosCount(), size)
    }
}
