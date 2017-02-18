package app.core;

import app.core.model.Item;

import java.util.concurrent.BlockingDeque;
import java.util.function.Predicate;

public interface MediaProvider {

    /**
     * Gets all media items of the specified user and puts them into the specified deque
     *
     * @param username the username of some instagram user
     * @param deque deque for items
     * @param itemsFilter only items that pass itemsFilter would be added to deque
     * @param includeProfilePicture if {@code true} user's profile picture will be added to as item to deque
     * @throws NullPointerException if the specified username is {@code null}
     * @throws NullPointerException if the specified deque is {@code null}
     * @throws IllegalArgumentException if the specified username is not valid
     */
    void getMedia(String username, BlockingDeque<Item> deque, Predicate<Item> itemsFilter, boolean includeProfilePicture, int itemsAmount);

}
