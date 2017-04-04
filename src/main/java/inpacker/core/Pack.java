package inpacker.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static inpacker.core.PackStatus.*;

public class Pack {

    private PackStatus status;
    private final List<PackItem> addedItems;
    private final List<PackItem> failedItems;

    public Pack() {
        status = PackStatus.INITIATED;
        addedItems = new ArrayList<>();
        failedItems = new ArrayList<>();
    }

    public PackStatus getStatus() {
        return status;
    }

    public boolean isDone() {
        return getStatus() == DONE;
    }

    public boolean isFailed() {
        return getStatus() == FAILED;
    }

    public int addedItemsAmount() {
        return addedItems.size();
    }

    public int failedItemsAmount() {
        return failedItems.size();
    }

    void processing() {
        if (status != INITIATED) throw new IllegalStateException();
        status = PROCESSING;
    }

    void failed() {
        if (status != PROCESSING) throw new IllegalStateException();
        status = FAILED;
    }

    void done() {
        if (status != PROCESSING) throw new IllegalStateException();
        status = PackStatus.DONE;
    }

    void newSuccessItem(PackItem item) {
        if (status != PROCESSING) throw new IllegalStateException();
        addedItems.add(item);
    }

    void newFailedItem(PackItem item) {
        if (status != PROCESSING) throw new IllegalStateException();
        failedItems.add(item);
    }
}
