package app.core.impl;

import app.core.model.Item;
import app.core.UserMediaProvider;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.function.Predicate;

@Service
public class UserMediaProviderImpl implements UserMediaProvider {

    private JsonParser jsonParser;

    public UserMediaProviderImpl() {
        jsonParser = new JsonParser();
    }

    @Override
    public void getUserMedia(String username, BlockingDeque<Item> deque, Predicate<Item> itemsFilter, int itemsAmount) {
        Objects.requireNonNull(username, "username is null");
        Objects.requireNonNull(deque, "deque is null");
        if (username.isEmpty()) {
            throw new IllegalArgumentException("username is not valid");
        }

        JsonObject resp;
        boolean moreAvailable;
        String queryString = "";
        int packedItemsAmount = 0;
        do {
            final HttpRequest req = HttpRequest.get(getUrl(username, queryString));
            resp = jsonParser.parse(req.body()).getAsJsonObject();
            moreAvailable = resp.get("more_available").getAsBoolean();
            final Item[] items = retrieveItems(resp.getAsJsonArray("items"));
            for (Item item : items) {
                if (itemsFilter.test(item)) {
                    deque.addLast(item);
                    if (++packedItemsAmount >= itemsAmount) break;
                }
            }
            queryString = "?max_id=" + items[items.length - 1].id;
        } while (moreAvailable && packedItemsAmount < itemsAmount);
        Item last = new Item("end", "end", 0, "end", "end");
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
            items[i] = parseItem(jsonItem);
        }
        return items;
    }

    private Item parseItem(JsonObject itemJson) {
        final String username = itemJson.get("user").getAsJsonObject().get("username").getAsString();
        final long createdTime = itemJson.get("created_time").getAsLong();
        final String id = itemJson.get("id").getAsString();
        final String type = itemJson.get("type").getAsString();
        String urlsObj = "image".equals(type) ? "images" : "videos";
        final String url = itemJson.get(urlsObj).getAsJsonObject()
                                   .get("standard_resolution").getAsJsonObject()
                                   .get("url").getAsString();
        return new Item(username, url, createdTime, type, id);
    }

    private String getUrlFromItem(JsonObject item) {
        return item.get("images").getAsJsonObject()
                   .get("standard_resolution").getAsJsonObject()
                   .get("url").getAsString();
    }

    private String getUrl(String username, String queryString) {
        return String.format("https://www.instagram.com/%s/media/%s", username, queryString);
    }

}
