package inpacker.px

import groovy.transform.Immutable
import inpacker.core.PackConfig

import java.util.function.BiFunction

import static java.lang.Math.min

@Immutable class PxPackConfig implements PackConfig<PxPackItem> {

    PxUser user
    int size

    BiFunction<Integer, PxPost, String> getFileNameCreator() {
        return {idx, post -> idx + post.extension()}
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
