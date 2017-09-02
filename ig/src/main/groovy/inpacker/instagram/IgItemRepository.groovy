package inpacker.instagram

import groovy.json.JsonSlurper
import inpacker.core.ItemRepository

import static inpacker.instagram.ApiUrls.userMediaUrl
import static inpacker.instagram.ApiUrls.userProfileUrl

class IgItemRepository implements ItemRepository<IgPackConfig, IgPackItem> {

    @Override
    void getPackItems(IgPackConfig config, Collection<IgPackItem> items) {
        String query = ''
        int packedItemsCount = 0
        boolean moreItemsAvailable = true
        while (moreItemsAvailable && packedItemsCount < config.size) {
            def json = new JsonSlurper().parse(new URL(userMediaUrl(config.user.username, query)))
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
        IgPost last = new IgPost('', '', '', '', 0)
        items << new IgPackItem(last, packedItemsCount, {i, p -> 'end'})
    }

    IgUser getInstagramUser(String username) {
        Objects.requireNonNull(username, 'username is null')
        if (username.trim().isEmpty())
            throw new IllegalArgumentException('username is empty')
        try {
            def user = new JsonSlurper().parse(new URL(userProfileUrl(username))).user
            return new IgUser(
                    instagramId: user.id,
                    username: user.username,
                    isPrivate: user.is_private,
                    fullName: user.full_name,
                    biography: user.biography,
                    profilePic: user.profile_pic_url_hd,
                    count: user.media.count,
                    isVerified: user.is_verified)
        } catch (Exception x) {
            return null
        }
    }

    private IgPost parseItem(item) {
        String url
        if (item.type == 'image' || item.videos == null) url = item.images.standard_resolution.url
        else url = item.videos.standard_resolution.url
        return new IgPost(
                username: item.user.username,
                url: url,
                createdTime: item.created_time.toLong(),
                type: item.type,
                id: item.id)
    }

}
