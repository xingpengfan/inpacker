package inpacker.px

class ApiUrls {

    private static final String API_VERSION = 'v1'
    private static final String API_URL = "https://api.500px.com/$API_VERSION"
    private static final int DEFAULT_IMAGE_SIZE = 2048
    private static final int DEFAULT_PAGE = 1

    static String profile(String username, String consumerKey) {
        "$API_URL/users/show?username=$username&consumer_key=$consumerKey"
    }

    static String photos(String username, String consumerKey) {
        getUserPhotosUrl(username, consumerKey, DEFAULT_IMAGE_SIZE, DEFAULT_PAGE)
    }

    static String photos(String username, String consumerKey, int imageSize, int page) {
        "$API_URL/photos?feature=user&username=$username&consumer_key=$consumerKey&image_size=$imageSize&page=$page"
    }

    private ApiUrls() {}
}
