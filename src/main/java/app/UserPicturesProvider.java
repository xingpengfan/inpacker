package app;

import java.util.concurrent.BlockingDeque;

public interface UserPicturesProvider {

    /**
     * Gets all pics urls of the specified user and puts them into deque
     *
     * @param username the username of instagram user
     * @param deque deque for urls
     * @throws NullPointerException if the specified username is {@code null}
     * @throws NullPointerException if the specified deque is {@code null}
     * @throws IllegalArgumentException if the specified username is not valid
     */
    void getUserPicturesUrls(String username, BlockingDeque<String> deque);

}
