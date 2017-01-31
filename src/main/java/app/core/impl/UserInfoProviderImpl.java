package app.core.impl;

import app.core.UserInfo;
import app.core.UserInfoProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserInfoProviderImpl implements UserInfoProvider {

    private JsonParser parser;

    public UserInfoProviderImpl() {
        parser = new JsonParser();
    }

    @Override
    public UserInfo getUserInfo(String username) {
        Objects.requireNonNull(username, "username is null");
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("username is empty");
        }

        final String url = getUrl(username);
        final HttpRequest req = HttpRequest.get(url);
        if (req.code() != 200) {
            return null;
        }

        return parseUserInfo(req.body());
    }

    private UserInfo parseUserInfo(String json) {
        final JsonObject userJsonObject = parser.parse(json).getAsJsonObject().get("user").getAsJsonObject();
        final UserInfo userInfo = new UserInfo();
        userInfo.instagramId = userJsonObject.get("id").getAsString();
        userInfo.username = userJsonObject.get("username").getAsString();
        userInfo.isPrivate = userJsonObject.get("is_private").getAsBoolean();
        final JsonElement fullName = userJsonObject.get("full_name");
        userInfo.fullName = fullName.isJsonNull() ? userInfo.username : fullName.getAsString();
        final JsonElement biography = userJsonObject.get("biography");
        userInfo.biography = biography.isJsonNull() ? "" : biography.getAsString();
        userInfo.profilePic = userJsonObject.get("profile_pic_url_hd").getAsString();
        userInfo.count = userJsonObject.get("media").getAsJsonObject().get("count").getAsInt();
        userInfo.isVerified = userJsonObject.get("is_verified").getAsBoolean();
        return userInfo;
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private String getUrl(String username) {
        return String.format("https://www.instagram.com/%s/?__a=1", username);
    }
}
