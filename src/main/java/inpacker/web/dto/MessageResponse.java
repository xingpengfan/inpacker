package inpacker.web.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static java.lang.String.format;

public class MessageResponse {

    @SerializedName("message")
    public String message;

    public static MessageResponse userNotFound(String username) {
        return new MessageResponse(format("user '%s' not found", username));
    }

    public static MessageResponse packNotFound(String pack) {
        return new MessageResponse(format("pack '%s' not found", pack));
    }

    private MessageResponse(String msg) {
        message = msg;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj instanceof MessageResponse
                && Objects.equals(message, ((MessageResponse) obj).message);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(message);
        return hash;
    }
}
