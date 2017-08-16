package inpacker.px

import groovy.json.JsonSlurper
import inpacker.core.ItemRepository
import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.DefaultAsyncHttpClient
import org.asynchttpclient.Response

import java.util.concurrent.ExecutionException

import static inpacker.px.ApiHelper.getUserPhotosUrl
import static inpacker.px.ApiHelper.getUserProfileUrl
import static java.util.Objects.requireNonNull

class PxItemRepository implements ItemRepository<PxPackConfig, PxPackItem> {

    private AsyncHttpClient httpClient
    private final String consumerKey

    PxItemRepository(String consumerKey) {
        this.consumerKey = consumerKey
        httpClient = new DefaultAsyncHttpClient()
    }

    @Override
    void getPackItems(PxPackConfig config, Collection<PxPackItem> items) {
        try {
            int packedItemsCount = 0
            boolean moreAvailable = true
            while (moreAvailable && packedItemsCount < config.numberOfItems()) {
                def future = httpClient.prepareGet(getUserPhotosUrl(config.user.username, consumerKey)).execute()
                Response resp = future.get()
                def json = new JsonSlurper().parseText(resp.getResponseBody())
                for (int i = 0; i < json.photos.size() && packedItemsCount < config.numberOfItems(); i++) {
                    def post = parsePost(json.photos[i])
                    def item = new PxPackItem(post, packedItemsCount + 1, config.getFileNameCreator())
                    if (config.test(item)) {
                        items << item
                        packedItemsCount++
                    }
                }
                moreAvailable = json.current_page.toInteger() < json.total_pages.toInteger()
            }
            PxPost last = new PxPost('', '', '', '', 0, 0, '', '')
            items << new PxPackItem(last, packedItemsCount, { i, p -> 'end' })
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    PxUser get500pxUser(String username) {
        requireNonNull(username, 'required non null username')
        def future = httpClient.prepareGet(getUserProfileUrl(username, consumerKey)).execute()
        Response resp
        try {
            resp = future.get()
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace()
            return null
        }
        if (resp.getStatusCode() != 200)
            return null
        else
            return parseUser(resp.getResponseBody())
    }

    private PxPost parsePost(postJson) {
        return new PxPost(
                id: postJson.id,
                name: postJson.name,
                description: postJson.description?:'no description',
                createdAt: postJson.created_at,
                width: postJson.width.toInteger(),
                height: postJson.height.toInteger(),
                url: postJson.image_url,
                imageFormat: postJson.image_format)
    }

    private PxUser parseUser(String json) {
        def u = new JsonSlurper().parseText(json).user
        return new PxUser(
                id: u.id,
                username: u.username,
                firstname: u.firstname,
                lastname: u.lastname,
                city: u.city,
                country: u.country,
                userpicUrl: u.userpic_url,
                coverUrl: u.cover_url,
                photosCount: u.photos_count)
    }
}
