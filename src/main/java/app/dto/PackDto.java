package app.dto;

import com.google.gson.annotations.SerializedName;

public class PackDto {

    @SerializedName("name")
    public String name;

    @SerializedName("status")
    public String status;

    public PackDto(String name, String status) {
        this.name = name;
        this.status = status;
    }
}
