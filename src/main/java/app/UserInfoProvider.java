package app;

public interface UserInfoProvider {

    /**
     * Returns UserInfo object that represents a user with the specified username
     * or {@code null} if user with the specified username not found
     *
     * @param username the username of instagram user
     * @return UserInfo object or {@code null} if user not found
     * @throws NullPointerException if the username is {@code null}
     * @throws IllegalArgumentException if the username is not valid
     */
    UserInfo getUserInfo(String username);
}
