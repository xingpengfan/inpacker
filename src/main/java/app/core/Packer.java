package app.core;

import java.util.concurrent.BlockingDeque;
import java.util.function.Function;

public interface Packer {

    /**
     * Creates pack with user media.
     * Pack is done when itemsDeque.takeLast() returns item with id "end"
     *
     * @param itemsDeque deque with items
     * @param getFileName function that returns file name for item
     */
    void pack(BlockingDeque<Item> itemsDeque, Function<Item, String> getFileName);
}
