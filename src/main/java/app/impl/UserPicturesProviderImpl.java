package app.impl;

import app.UserPicturesProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;

@Service
public class UserPicturesProviderImpl implements UserPicturesProvider {

    private JsonParser jsonParser;

    public UserPicturesProviderImpl() {
        jsonParser = new JsonParser();
    }

    @Override
    public void getUserPicturesUrls(String username, BlockingDeque<String> deque) {
        Objects.requireNonNull(username, "username is null");
        Objects.requireNonNull(deque, "deque is null");
        if (username.isEmpty()) {
            throw new IllegalArgumentException("username is not valid");
        }

        JsonObject resp;
        boolean moreAvailable;
        String queryString = "";
        do {
            final HttpRequest req = HttpRequest.get(getUrl(username, queryString));
            resp = jsonParser.parse(req.body()).getAsJsonObject();
            moreAvailable = resp.get("more_available").getAsBoolean();
            final JsonArray items = resp.getAsJsonArray("items");
            queryString = "?max_id=" + getIdOfLastItem(items);
            retrieveUrls(items, deque);
        } while (moreAvailable);
        deque.addLast("end");
    }

    private void retrieveUrls(JsonArray items, BlockingDeque<String> deque) {
        for (JsonElement item : items) {
            deque.addLast(getUrlFromItem(item.getAsJsonObject()));
        }
    }

    private String getUrlFromItem(JsonObject item) {
        return item.get("images").getAsJsonObject()
                   .get("standard_resolution").getAsJsonObject()
                   .get("url").getAsString();
    }

    private String getIdOfLastItem(JsonArray items) {
        return items.get(items.size() - 1).getAsJsonObject().get("id").getAsString();
    }

    private String getUrl(String username, String queryString) {
        return String.format("https://www.instagram.com/%s/media/%s", username, queryString);
    }

}
