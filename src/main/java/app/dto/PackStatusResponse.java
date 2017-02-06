package app.dto;

import com.google.gson.annotations.SerializedName;

public class PackStatusResponse {

    @SerializedName("name")
    public String name;

    @SerializedName("status")
    public boolean status;


    public PackStatusResponse() {}

    public PackStatusResponse(String packName, boolean packStatus) {
        this.name = packName;
        this.status = packStatus;
    }
}
