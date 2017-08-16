package inpacker.px

class ApiHelper {

    private static final String API_URL_BASE = 'https://api.500px.com/v1'
    private static final int DEFAULT_IMAGE_SIZE = 2048
    private static final int DEFAULT_PAGE = 1

    static String getUserProfileUrl(String username, String consumerKey) {
        "$API_URL_BASE/users/show?username=$username&consumer_key=$consumerKey"
    }

    static String getUserPhotosUrl(String username, String consumerKey) {
        getUserPhotosUrl(username, consumerKey, DEFAULT_IMAGE_SIZE, DEFAULT_PAGE)
    }

    static String getUserPhotosUrl(String username, String consumerKey, int imageSize, int page) {
        "$API_URL_BASE/photos?feature=user&username=$username&consumer_key=$consumerKey&image_size=$imageSize&page=$page"
    }

    private ApiHelper() {}
}
