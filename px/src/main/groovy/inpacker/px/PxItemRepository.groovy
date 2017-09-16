package inpacker.px

import groovy.json.JsonSlurper
import inpacker.core.ItemRepository

import static java.util.Objects.requireNonNull

class PxItemRepository implements ItemRepository<PxPackConfig, PxPackItem> {

    private final JsonSlurper parser
    private final String consumerKey

    PxItemRepository(String consumerKey) {
        this.consumerKey = consumerKey
        parser = new JsonSlurper()
    }

    @Override
    void getPackItems(PxPackConfig config, Collection<PxPackItem> items) {
        int packedItemsCount = 0
        boolean moreAvailable = true
        while (moreAvailable && packedItemsCount < config.numberOfItems()) {
            def json = parser.parse(new URL(ApiUrls.photos()))
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
    }

    PxUser get500pxUser(String username) {
        requireNonNull(username, 'required non null username')
        if (username.trim().isEmpty())
            throw new IllegalArgumentException('username is empty')
        try {
            def json = parser.parse(new URL(ApiUrls.profile(username, consumerKey)))
            return new PxUser(
                    id: json.user.id,
                    username: json.user.username,
                    firstname: json.user.firstname,
                    lastname: json.user.lastname,
                    city: json.user.city,
                    country: json.user.country,
                    userpicUrl: json.user.userpic_url,
                    coverUrl: json.user.cover_url,
                    photosCount: json.user.photos_count)
        } catch (Exception x) {
            return null
        }
    }

    private PxPost parsePost(postJson) {
        return new PxPost(
                id: postJson.id,
                name: postJson.name,
                description: postJson.description ?: 'no description',
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
