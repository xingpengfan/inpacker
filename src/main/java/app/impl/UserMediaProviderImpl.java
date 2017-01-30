package app.impl;

import app.Item;
import app.UserMediaProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;

@Service
public class UserMediaProviderImpl implements UserMediaProvider {

    private JsonParser jsonParser;

    public UserMediaProviderImpl() {
        jsonParser = new JsonParser();
    }

    @Override
    public void getUserPicturesUrls(String username, BlockingDeque<Item> deque) {
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
            final Item[] items = retrieveItems(resp.getAsJsonArray("items"));
            for (Item item : items) {
                deque.addLast(item);
            }
            queryString = "?max_id=" + items[items.length - 1].id;
        } while (moreAvailable);
        Item last = new Item();
        last.createdTime = -1;
        deque.addLast(last);
    }

    private void retrieveUrls(JsonArray items, BlockingDeque<String> deque) {
        for (JsonElement item : items) {
            deque.addLast(getUrlFromItem(item.getAsJsonObject()));
        }
    }

    private Item[] retrieveItems(JsonArray jsonItems) {
        Item[] items = new Item[jsonItems.size()];
        for (int i = 0; i < jsonItems.size(); i++) {
            final JsonObject jsonItem = jsonItems.get(i).getAsJsonObject();
            Item item = new Item();
            item.username = jsonItem.get("user").getAsJsonObject().get("username").getAsString();
            item.url = jsonItem.get("images").getAsJsonObject()
                               .get("standard_resolution").getAsJsonObject()
                               .get("url").getAsString();
            item.captionText = jsonItem.get("caption").isJsonNull() ? ""
                               : jsonItem.get("caption").getAsJsonObject().get("text").getAsString();
            item.createdTime = jsonItem.get("created_time").getAsLong();
            item.id = jsonItem.get("id").getAsString();
            item.type = jsonItem.get("type").getAsString();
            items[i] = item;
        }
        return items;
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
