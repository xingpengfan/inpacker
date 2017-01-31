package app.core;

import java.time.DateTimeException;

public interface ItemNameHelper {

    /**
     * Creates and returns a name for the specified item
     *
     * @param item the item to be named
     * @return name of the specified item
     * @throws NullPointerException if the specified item is {@code null}
     * @throws DateTimeException if the item.createdTime exceeds the supported range
     */
    String getName(Item item);
}
