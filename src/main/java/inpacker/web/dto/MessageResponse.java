package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {

    @SerializedName("message")
    public String message;

    public MessageResponse(String msg) {
        message = msg;
    }
}
