package app.core.impl;

import app.core.UserInfo;
import app.core.UserInfoProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserInfoProviderImpl implements UserInfoProvider {

    private JsonParser parser;
    private Gson gson;

    public UserInfoProviderImpl() {
        parser = new JsonParser();
        gson = new Gson();
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
        final JsonObject user = parser.parse(json).getAsJsonObject().get("user").getAsJsonObject();
        final UserInfo userInfo = gson.fromJson(user, UserInfo.class);
        userInfo.count = user.get("media").getAsJsonObject().get("count").getAsInt();
        return userInfo;
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty();
    }

    private String getUrl(String username) {
        return String.format("https://www.instagram.com/%s/?__a=1", username);
    }
}
