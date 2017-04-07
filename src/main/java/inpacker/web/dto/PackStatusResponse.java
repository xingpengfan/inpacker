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

    @SerializedName("packed_amount")
    private int packedItemsAmount;

    @SerializedName("failed_amount")
    private int failedItemsAmount;

    public PackStatusResponse(String id, boolean isDone, boolean isFailed, int packedItemsAmount, int failedItemsAmount) {
        this.id = id;
        this.isDone = isDone;
        this.isFailed = isFailed;
        this.packedItemsAmount = packedItemsAmount;
        this.failedItemsAmount = failedItemsAmount;
    }

    public PackStatusResponse(Pack pack) {
        this(pack.getId(), pack.isDone(), pack.isFailed(), pack.addedItemsAmount(), pack.failedItemsAmount());
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
                && packedItemsAmount == that.packedItemsAmount
                && failedItemsAmount == that.failedItemsAmount
                && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(id);
        hash = 31 * hash + Boolean.hashCode(isDone);
        hash = 31 * hash + Boolean.hashCode(isFailed);
        hash = 31 * hash + packedItemsAmount;
        hash = 31 * hash + failedItemsAmount;
        return hash;
    }
}
