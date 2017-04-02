package inpacker.instagram;

import com.google.gson.annotations.SerializedName;

public class Pack {

    @SerializedName("name")
    private String name;

    @SerializedName("ready")
    private boolean ready;

    @SerializedName("amount")
    private int packedItemsAmount;

    public Pack(String name) {
        this.name = name;
        ready = false;
        packedItemsAmount = 0;
    }

    public void ready() {
        ready = true;
    }

    public boolean isReady() {
        return ready;
    }

    public String getName() {
        return name;
    }

    public void newItem() {
        packedItemsAmount++;
    }

}
