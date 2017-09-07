package inpacker.instagram

class InstagramApiUrls {

    static String profile(String username) {
        "https://www.instagram.com/${username}/?__a=1"
    }

    static String media(String username, String query) {
        "https://www.instagram.com/${username}/media/${query}"
    }

    private InstagramApiUrls() {}
}
