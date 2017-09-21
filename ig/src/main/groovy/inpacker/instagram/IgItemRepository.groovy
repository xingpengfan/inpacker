package inpacker.instagram

import groovy.json.JsonSlurper
import inpacker.core.ItemRepository

class IgItemRepository implements ItemRepository<IgPackConfig, IgPackItem> {

    private final JsonSlurper parser
    private final int maxPackSize

    IgItemRepository(int maxPackSize) {
        if (maxPackSize <= 0)
            throw new IllegalArgumentException("maxPackSize should be positive")
        this.maxPackSize = maxPackSize
        parser = new JsonSlurper()
    }

    @Override
    void getPackItems(IgPackConfig config, Collection<IgPackItem> items) {
        String query = ''
        int packed = 0
        boolean moreAvailable = true
        int size = Math.min(maxPackSize, config.numberOfItems())
        while (moreAvailable && packed < size) {
            def json = parser.parse(new URL(InstagramApiUrls.media(config.user.username, query)))
            for (int i = 0; i < json.items.size() && packed < size; i++) {
                def post = parseItem(json.items.get(i))
                def item = new IgPackItem(post, packed+1, config.getFileNameCreator())
                if (config.test(item)) {
                    items << item
                    packed++
                }
            }
            query = "?max_id=${json.items.get(json.items.size() - 1).id}"
            moreAvailable = json.more_available
        }
        items << new IgPackItem(new IgPost('', '', '', '', 0), packed, {i, p -> 'end'})
    }

    IgUser getInstagramUser(String username) {
        Objects.requireNonNull(username, 'username is null')
        if (username.trim().isEmpty())
            throw new IllegalArgumentException('username is empty')
        try {
            def user = parser.parse(new URL(InstagramApiUrls.profile(username))).user
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
        if (item.type == 'image' || item.videos == null)
            url = item.images.standard_resolution.url
        else
            url = item.videos.standard_resolution.url
        url = normalizeUrl(url)
        return new IgPost(
                username: item.user.username,
                url: url,
                createdTime: item.created_time.toLong(),
                type: item.type,
                id: item.id)
    }

    private String normalizeUrl(String url) {
        return url.substring(0, url.indexOf('.com/t')+6) + url.substring(url.lastIndexOf('/'))
    }

}
