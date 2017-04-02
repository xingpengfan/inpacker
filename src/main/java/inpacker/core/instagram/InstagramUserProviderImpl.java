package inpacker.core.instagram;

import inpacker.core.InstagramUserProvider;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class InstagramUserProviderImpl implements InstagramUserProvider {

    private AsyncHttpClient asyncHttpClient;
    private JsonParser parser;

    public InstagramUserProviderImpl() {
        asyncHttpClient = new DefaultAsyncHttpClient();
        parser = new JsonParser();
    }

    @Override
    public InstagramUser getInstagramUser(String username) {
        Objects.requireNonNull(username, "username is null");
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("username is empty");
        }
        final Response response;
        final String url = getUrl(username);
        final ListenableFuture<Response> f = asyncHttpClient.prepareGet(url).execute();
        try {
            response = f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        if (response.getStatusCode() != 200)
            return null;
        else
            return parseUser(response.getResponseBody());
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
