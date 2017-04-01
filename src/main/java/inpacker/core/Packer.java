package inpacker.core;

import inpacker.core.model.Item;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.function.BiFunction;

public interface Packer {

    /**
     * Creates pack with user media.
     * Pack is done when itemsDeque.takeLast() returns item with id "end"
     *
     * @param itemsDeque deque with items
     * @param packPath path to pack
     * @param fileNameCreator function that consumes item and index and returns file name for item
     * @param newItemCallback Runnable object that will be called after each item is added to pack
     */
    void pack(BlockingDeque<Item> itemsDeque, File packPath, BiFunction<Item, Integer, String> fileNameCreator,
              Runnable newItemCallback);
}
