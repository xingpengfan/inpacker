package app.core.impl;

import app.core.model.InstagramUser;
import app.core.InstagramUserProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InstagramUserProviderImpl implements InstagramUserProvider {

    private JsonParser parser;

    public InstagramUserProviderImpl() {
        parser = new JsonParser();
    }

    @Override
    public InstagramUser getInstagramUser(String username) {
        Objects.requireNonNull(username, "username is null");
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("username is empty");
        }
        final String url = getUrl(username);
        final HttpRequest req = HttpRequest.get(url);
        if (!req.ok())
            return null;
        else
            return parseUser(req.body());
    }

    private InstagramUser parseUser(String json) {
        final JsonObject uj = parser.parse(json).getAsJsonObject().get("user").getAsJsonObject();
        final InstagramUser user = new InstagramUser();
        user.instagramId = uj.get("id").getAsString();
        user.username = uj.get("username").getAsString();
        user.isPrivate = uj.get("is_private").getAsBoolean();
        final JsonElement fullName = uj.get("full_name");
        user.fullName = fullName.isJsonNull() ? user.username : fullName.getAsString();
        final JsonElement biography = uj.get("biography");
        user.biography = biography.isJsonNull() ? "" : biography.getAsString();
        user.profilePic = uj.get("profile_pic_url_hd").getAsString();
        user.count = uj.get("media").getAsJsonObject().get("count").getAsInt();
        user.isVerified = uj.get("is_verified").getAsBoolean();
        return user;
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private String getUrl(String username) {
        return String.format("https://www.instagram.com/%s/?__a=1", username);
    }
}
