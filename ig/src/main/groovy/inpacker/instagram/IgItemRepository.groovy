package inpacker.instagram

import groovy.json.JsonSlurper
import inpacker.core.ItemRepository
import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.DefaultAsyncHttpClient
import org.asynchttpclient.ListenableFuture
import org.asynchttpclient.Response

import java.util.concurrent.ExecutionException

class IgItemRepository implements ItemRepository<IgPackConfig, IgPackItem> {

    private AsyncHttpClient asyncHttpClient

    IgItemRepository() {
        asyncHttpClient = new DefaultAsyncHttpClient()
    }

    @Override
    void getPackItems(IgPackConfig config, Collection<IgPackItem> items) {
        String query = ''
        int packedItemsCount = 0
        boolean moreItemsAvailable = true
        while (moreItemsAvailable && packedItemsCount < config.size) {
            ListenableFuture<Response> f = asyncHttpClient.prepareGet(getUrl(config.user.username, query)).execute()
            Response response = f.get()
            def jsonObj = new JsonSlurper().parseText(response.getResponseBody())
            IgPost post
            for (int i = 0; i < jsonObj.items.size(); i++) {
                post = parseItem(jsonObj.items.get(i))
                IgPackItem item = new IgPackItem(post, packedItemsCount+1, config.getFileNameCreator())
                if (config.test(item)) {
                    items.add(item)
                    if (++packedItemsCount >= config.size) break
                }
            }
            query = "?max_id=${jsonObj.items.get(jsonObj.items.size() - 1).id}"
            moreItemsAvailable = jsonObj.more_available
        }
        IgPost last = new IgPost(username: 'end', url: 'end', type: 'end', id: 'end', createdTime: 0)
        items.add(new IgPackItem(last, packedItemsCount, {i, p -> 'end'}))
    }

    IgUser getInstagramUser(String username) {
        Objects.requireNonNull(username, 'username is null')
        String url = "https://www.instagram.com/${username}/?__a=1"
        ListenableFuture<Response> f = asyncHttpClient.prepareGet(url).execute()
        Response response
        try {
            response = f.get()
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTracke()
            return null
        }
        if (response.getStatusCode() != 200) return null
        else return parseUser(response.getResponseBody())

    }

    private IgPost parseItem(item) {
        def url
        String type = item.type
        if (type == 'image') url = item.images.standard_resolution.url
        else {
            if (item.videos == null) url = item.images.standard_resolution.url
            else url = item.videos.standard_resolution.url
        }
        return new IgPost(
                username: item.user.username,
                url: url,
                createdTime: item.created_time.toLong(),
                type: type,
                id: item.id)
    }

    private static String getUrl(String username, String query) {
        return "https://www.instagram.com/${username}/media/${query}"
    }

    private IgUser parseUser(json) {
        def userJson = new JsonSlurper().parseText(json).user
        return new IgUser(
                instagramId: userJson.id,
                username: userJson.username,
                isPrivate: userJson.is_private,
                fullName: userJson.full_name,
                biography: userJson.biography,
                profilePic: userJson.profile_pic_url_hd,
                count: userJson.media.count,
                isVerified: userJson.is_verified)
    }
}
