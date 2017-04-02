package inpacker.core;

import inpacker.core.model.InstagramPost;

import java.util.concurrent.BlockingDeque;
import java.util.function.Predicate;

public interface MediaProvider {

    void getMedia(String username, BlockingDeque<InstagramPost> deque,
                  Predicate<InstagramPost> itemsFilter, boolean includeProfilePicture, int itemsAmount);

}
