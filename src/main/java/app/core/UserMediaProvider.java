package app.core;

import java.util.concurrent.BlockingDeque;

public interface UserMediaProvider {

    /**
     * Gets all media items of the specified user and puts them into the specified deque
     *
     * @param username the username of some instagram user
     * @param deque deque for urls
     * @throws NullPointerException if the specified username is {@code null}
     * @throws NullPointerException if the specified deque is {@code null}
     * @throws IllegalArgumentException if the specified username is not valid
     */
    void getUserPicturesUrls(String username, BlockingDeque<Item> deque);

}
