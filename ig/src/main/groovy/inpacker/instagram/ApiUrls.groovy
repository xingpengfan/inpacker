package inpacker.instagram

class ApiUrls {

    static String userProfileUrl(String username) {
        "https://www.instagram.com/${username}/?__a=1"
    }

    static String userMediaUrl(String username, String query) {
        "https://www.instagram.com/${username}/media/${query}"
    }

    private ApiUrls() {}
}
