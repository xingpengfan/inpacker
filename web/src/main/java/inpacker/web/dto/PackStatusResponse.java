package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.core.Pack;

import java.util.Objects;

public class PackStatusResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("is_done")
    private boolean isDone;

    @SerializedName("is_failed")
    private boolean isFailed;

    @SerializedName("packed_count")
    private int packedItemsCount;

    @SerializedName("failed_count")
    private int failedItemsCount;

    @SerializedName("items_count")
    private int numberOfItems;

    public PackStatusResponse(Pack pack) {
        id = pack.getId();
        isDone = pack.isDone();
        isFailed = pack.isFailed();
        packedItemsCount = pack.addedItemsAmount();
        failedItemsCount = pack.failedItemsAmount();
        numberOfItems = pack.getItemsCount();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PackStatusResponse)) {
            return false;
        }
        final PackStatusResponse that = (PackStatusResponse) obj;
        return isDone == that.isDone
                && isFailed == that.isFailed
                && packedItemsCount == that.packedItemsCount
                && failedItemsCount == that.failedItemsCount
                && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(id);
        hash = 31 * hash + Boolean.hashCode(isDone);
        hash = 31 * hash + Boolean.hashCode(isFailed);
        hash = 31 * hash + packedItemsCount;
        hash = 31 * hash + failedItemsCount;
        return hash;
    }
}
