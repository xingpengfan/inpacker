package lib;

public interface UserInfoProvider {

    /**
     *
     * @param username the username of instagram user
     * @return UserInfo object
     */
    UserInfo getUserInfo(String username);
}
