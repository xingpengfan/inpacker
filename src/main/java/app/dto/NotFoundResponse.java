package app.dto;

import com.google.gson.annotations.SerializedName;

public class NotFoundResponse {

    @SerializedName("message")
    public String message;

    public NotFoundResponse() {
        message = "Not Found";
    }
}
