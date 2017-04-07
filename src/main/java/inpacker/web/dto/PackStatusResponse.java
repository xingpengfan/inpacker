package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.core.Pack;

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
}
