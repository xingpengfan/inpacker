package app.core.impl;

import app.core.model.Item;
import app.core.MediaProvider;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

@Service
public class MediaProviderImpl implements MediaProvider {

    private AsyncHttpClient asyncHttpClient;
    private JsonParser      jsonParser;

    public MediaProviderImpl() {
        asyncHttpClient = new DefaultAsyncHttpClient();
        jsonParser = new JsonParser();
    }

    @Override
    public void getMedia(String username, BlockingDeque<Item> deque, Predicate<Item> itemsFilter,
                         boolean includeProfilePicture, int itemsAmount) {
        Objects.requireNonNull(username, "username is null");
        Objects.requireNonNull(deque, "deque is null");
        if (username.isEmpty()) {
            throw new IllegalArgumentException("username is not valid");
        }

        JsonObject respJson;
        boolean moreAvailable;
        String queryString = "";
        int packedItemsAmount = 0;
        do {
            final ListenableFuture<Response> f =
                    asyncHttpClient.prepareGet(getUrl(username, queryString)).execute();
            final Response response;
            try {
                response = f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            respJson = jsonParser.parse(response.getResponseBody()).getAsJsonObject();
            moreAvailable = respJson.get("more_available").getAsBoolean();
            final JsonArray itemsJsonArray = respJson.getAsJsonArray("items");
            final List<Item> items = retrieveItems(itemsJsonArray);
            if (includeProfilePicture && packedItemsAmount == 0) {
                deque.addLast(profilePictureItem(itemsJsonArray.get(0).getAsJsonObject()));
            }
            for (Item item : items)
                if (itemsFilter.test(item)) {
                    deque.addLast(item);
                    if (++packedItemsAmount >= itemsAmount) break;
                }
            queryString = "?max_id=" + items.get(items.size()-1).id;
        } while (moreAvailable && packedItemsAmount < itemsAmount);
        Item last = new Item("end", "end", 0, "end", "end");
        deque.addLast(last);
    }

    private void retrieveUrls(JsonArray items, BlockingDeque<String> deque) {
        for (JsonElement item : items) {
            deque.addLast(getUrlFromItem(item.getAsJsonObject()));
        }
    }

    private List<Item> retrieveItems(JsonArray jsonItems) {
        List<Item> items = new ArrayList<>(jsonItems.size());
        for (int i = 0; i < jsonItems.size(); i++) {
            final JsonObject jsonItem = jsonItems.get(i).getAsJsonObject();
            try {
                items.add(parseItem(jsonItem));
            } catch (RuntimeException parseItemException) {
                System.out.println("Parse item exception: " + parseItemException.getMessage());
            }
        }
        return items;
    }

    private Item parseItem(JsonObject itemJson) {
        final String username = itemJson.get("user").getAsJsonObject().get("username").getAsString();
        final long createdTime = itemJson.get("created_time").getAsLong();
        final String id = itemJson.get("id").getAsString();
        String type = itemJson.get("type").getAsString();
        String urlsObj = "image".equals(type) ? "images" : "videos";
        if (urlsObj.equals("videos") && itemJson.get(urlsObj) == null) {
            urlsObj = "images";
            type = "image";
        }
        final String url = itemJson.get(urlsObj).getAsJsonObject()
                                   .get("standard_resolution").getAsJsonObject()
                                   .get("url").getAsString();
        return new Item(username, url, createdTime, type, id);
    }

    private Item profilePictureItem(JsonObject itemJson) {
        final Item item = parseItem(itemJson);
        String profilePicUrl = itemJson.get("user").getAsJsonObject().get("profile_picture").getAsString();
        profilePicUrl = maxProfilePictureSize(profilePicUrl);
        return new Item(item.username, profilePicUrl, item.createdTime + 1,
                        "image", item.username + "_profile_picture");
    }

    private String maxProfilePictureSize(String str) {
        final int beg = str.indexOf(".com/t") + 6;
        final int end = str.lastIndexOf('/');
        return str.substring(0, beg) + str.substring(end);
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
