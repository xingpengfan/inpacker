package inpacker.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static inpacker.core.PackStatus.*;

public class Pack {

    private final String id;
    private File file;
    private PackStatus status;
    private final List<PackItem> addedItems;
    private final List<PackItem> failedItems;

    public Pack(String packId) {
        id = packId;
        status = PackStatus.INITIATED;
        addedItems = new ArrayList<>();
        failedItems = new ArrayList<>();
    }

    public File getPackFile() {
        return file;
    }

    public String getId() {
        return id;
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
        if (status != INITIATED) throw new IllegalStateException("pack status is not INITIATED");
        status = PROCESSING;
    }

    void failed() {
        if (status != PROCESSING) throw new IllegalStateException("only PROCESSING pack could be failed");
        status = FAILED;
    }

    void done(File packFile) {
        Objects.requireNonNull(packFile, "packFile is null");
        if (!packFile.exists()) throw new IllegalArgumentException("packFile does not exist");
        if (status != PROCESSING) throw new IllegalStateException("only PROCESSING pack could be done");
        file = packFile;
        status = PackStatus.DONE;
    }

    void newSuccessItem(PackItem item) {
        if (status != PROCESSING) throw new IllegalStateException("items could be added only in PROCESSING pack");
        addedItems.add(item);
    }

    void newFailedItem(PackItem item) {
        if (status != PROCESSING) throw new IllegalStateException("items could be added only in PROCESSING pack");
        failedItems.add(item);
    }
}
