package app.core;

import app.core.model.Item;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.function.Function;

public interface Packer {

    /**
     * Creates pack with user media.
     * Pack is done when itemsDeque.takeLast() returns item with id "end"
     *
     * @param itemsDeque deque with items
     * @param packPath path to pack
     * @param getFileName function that returns file name for item
     */
    void pack(BlockingDeque<Item> itemsDeque, File packPath, Function<Item, String> getFileName);
}
