package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.core.Pack;

public class PackStatusResponse {

    @SerializedName("name")
    private String name;

    @SerializedName("ready")
    private boolean ready;

    @SerializedName("amount")
    private int packedItemsAmount;

    public PackStatusResponse(String name, boolean ready, int packedItemsAmount) {
        this.name = name;
        this.ready = ready;
        this.packedItemsAmount = packedItemsAmount;
    }

    public PackStatusResponse(Pack pack) {
        this(pack.getName(), pack.isDone(), pack.addedItemsAmount());
    }
}
