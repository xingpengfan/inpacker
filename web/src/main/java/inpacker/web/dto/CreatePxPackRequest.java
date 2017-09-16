package inpacker.web.dto;

import inpacker.px.PxPackConfig;
import inpacker.px.PxUser;

import com.google.gson.annotations.SerializedName;

public class CreatePxPackRequest {

    @SerializedName("username")
    public String username;

    private static final int MAX_ITEMS_PER_PACK = 30;

    public CreatePxPackRequest(String username) {
        this.username = username;
    }

    public PxPackConfig config(PxUser user) {
        return new PxPackConfig(user, MAX_ITEMS_PER_PACK);
    }
}
