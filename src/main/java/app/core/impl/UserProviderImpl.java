package app.core.impl;

import app.core.User;
import app.core.UserProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserProviderImpl implements UserProvider {

    private JsonParser parser;

    public UserProviderImpl() {
        parser = new JsonParser();
    }

    @Override
    public User getUser(String username) {
        Objects.requireNonNull(username, "username is null");
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("username is empty");
        }

        final String url = getUrl(username);
        final HttpRequest req = HttpRequest.get(url);
        if (req.code() != 200) {
            return null;
        }

        return parseUser(req.body());
    }

    private User parseUser(String json) {
        final JsonObject userJsonObject = parser.parse(json).getAsJsonObject().get("user").getAsJsonObject();
        final User user = new User();
        user.instagramId = userJsonObject.get("id").getAsString();
        user.username = userJsonObject.get("username").getAsString();
        user.isPrivate = userJsonObject.get("is_private").getAsBoolean();
        final JsonElement fullName = userJsonObject.get("full_name");
        user.fullName = fullName.isJsonNull() ? user.username : fullName.getAsString();
        final JsonElement biography = userJsonObject.get("biography");
        user.biography = biography.isJsonNull() ? "" : biography.getAsString();
        user.profilePic = userJsonObject.get("profile_pic_url_hd").getAsString();
        user.count = userJsonObject.get("media").getAsJsonObject().get("count").getAsInt();
        user.isVerified = userJsonObject.get("is_verified").getAsBoolean();
        return user;
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private String getUrl(String username) {
        return String.format("https://www.instagram.com/%s/?__a=1", username);
    }
}
