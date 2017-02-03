package app.dto;

import com.google.gson.annotations.SerializedName;

public class PackStatusResponse {

    @SerializedName("packName")
    public String packName;

    @SerializedName("packStatus")
    public boolean packStatus;


    public PackStatusResponse() {}

    public PackStatusResponse(String packName, boolean packStatus) {
        this.packName = packName;
        this.packStatus = packStatus;
    }
}
