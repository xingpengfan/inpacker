package inpacker.core.instagram;

import inpacker.core.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutionException;

public class IgRepository implements Repository {

    private AsyncHttpClient asyncHttpClient;
    private JsonParser      jsonParser;

    public IgRepository() {
        asyncHttpClient = new DefaultAsyncHttpClient();
        jsonParser = new JsonParser();
    }

    @Override
    public void getInstagramPosts(IgPackConfig conf, BlockingDeque<InstagramPost> deque) {
        JsonObject respJson;
        boolean moreAvailable;
        String queryString = "";
        int packedItemsAmount = 0;
        do {
            final ListenableFuture<Response> f =
                    asyncHttpClient.prepareGet(getUrl(conf.username, queryString)).execute();
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
            final List<InstagramPost> posts = retrieveItems(itemsJsonArray);
            if (conf.includeProfilePicture && packedItemsAmount == 0) {
                deque.addLast(profilePictureItem(itemsJsonArray.get(0).getAsJsonObject()));
            }
            for (InstagramPost post: posts)
                if (conf.test(post)) {
                    deque.addLast(post);
                    if (++packedItemsAmount >= conf.amount) break;
                }
            queryString = "?max_id=" + posts.get(posts.size()-1).id;
        } while (moreAvailable && packedItemsAmount < conf.amount);
        final InstagramPost last = new InstagramPost("end", "end", 0, "end", "end");
        deque.addLast(last);
    }

    private void retrieveUrls(JsonArray items, BlockingDeque<String> deque) {
        for (JsonElement item : items) {
            deque.addLast(getUrlFromItem(item.getAsJsonObject()));
        }
    }

    private List<InstagramPost> retrieveItems(JsonArray jsonItems) {

        List<InstagramPost> items = new ArrayList<>(jsonItems.size());
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

    private InstagramPost parseItem(JsonObject itemJson) {
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
        return new InstagramPost(username, url, createdTime, type, id);
    }

    private InstagramPost profilePictureItem(JsonObject itemJson) {
        final InstagramPost veryFirstPost = parseItem(itemJson);
        String profilePicUrl = itemJson.get("user").getAsJsonObject().get("profile_picture").getAsString();
        profilePicUrl = maxProfilePictureSize(profilePicUrl);
        return new InstagramPost(veryFirstPost.username, profilePicUrl, veryFirstPost.createdTime+1,
                        "image", veryFirstPost.username + "_profile_picture");
    }

    private String maxProfilePictureSize(String profilePicUrl) {
        int beg = profilePicUrl.indexOf(".com/t");
        if (beg == -1) beg = profilePicUrl.indexOf(".net/t");
        beg += 6;
        final int end = profilePicUrl.lastIndexOf('/');
        return profilePicUrl.substring(0, beg) + profilePicUrl.substring(end);
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
