package inpacker.instagram

import groovy.json.JsonSlurper
import inpacker.core.ItemRepository
import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.DefaultAsyncHttpClient
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
            def f = asyncHttpClient.prepareGet(userMediaUrl(config.user.username, query)).execute()
            def response = f.get()
            def json = new JsonSlurper().parseText(response.getResponseBody())
            for (int i = 0; i < json.items.size() && packedItemsCount < config.size; i++) {
                def post = parseItem(json.items.get(i))
                def item = new IgPackItem(post, packedItemsCount+1, config.getFileNameCreator())
                if (config.test(item)) {
                    items << item
                    packedItemsCount++
                }
            }
            query = "?max_id=${json.items.get(json.items.size() - 1).id}"
            moreItemsAvailable = json.more_available
        }
        IgPost last = new IgPost(username: 'end', url: 'end', type: 'end', id: 'end', createdTime: 0)
        items << new IgPackItem(last, packedItemsCount, {i, p -> 'end'})
    }

    IgUser getInstagramUser(String username) {
        Objects.requireNonNull(username, 'username is null')
        def url = "https://www.instagram.com/${username}/?__a=1"
        def f = asyncHttpClient.prepareGet(url).execute()
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
        if (item.type == 'image' || item.videos == null) url = item.images.standard_resolution.url
        else url = item.videos.standard_resolution.url
        return new IgPost(
                username: item.user.username,
                url: url,
                createdTime: item.created_time.toLong(),
                type: item.type,
                id: item.id)
    }

    private static String userMediaUrl(String username, String query) {
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
