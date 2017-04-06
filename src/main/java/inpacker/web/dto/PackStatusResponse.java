package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;
import inpacker.core.Pack;

import java.util.Objects;

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
        this(pack.getId(), pack.isDone(), pack.addedItemsAmount());
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
        return ready == that.ready
                && packedItemsAmount == that.packedItemsAmount
                && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(name);
        hash = 31 * hash + Boolean.hashCode(ready);
        hash = 31 * hash + packedItemsAmount;
        return hash;
    }
}
