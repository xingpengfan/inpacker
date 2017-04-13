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
    private final int itemsCount;

    public Pack(String packId) {
        this(packId, -1);
    }

    public Pack(String packId, int numberOfItems) {
        id = packId;
        itemsCount = numberOfItems;
        status = PackStatus.INITIATED;
        addedItems = new ArrayList<>();
        failedItems = new ArrayList<>();
    }

    public File getFile() {
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

    public int getItemsCount() {
        return itemsCount;
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
        Objects.requireNonNull(packFile, "required non null packFile");
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pack)) {
            return false;
        }
        final Pack that = (Pack) obj;
        return Objects.equals(id, that.id)
                && Objects.equals(file, that.file)
                && Objects.equals(status, that.status)
                && addedItems.equals(that.addedItems)
                && failedItems.equals(that.failedItems);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(id);
        hash = 31 * hash + Objects.hashCode(file);
        hash = 31 * hash + Objects.hashCode(status);
        hash = 31 * hash + addedItems.hashCode();
        hash = 31 * hash + failedItems.hashCode();
        return hash;
    }
}
