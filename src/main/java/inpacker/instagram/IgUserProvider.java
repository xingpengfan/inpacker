package inpacker.instagram;

import inpacker.instagram.IgUser;

public interface IgUserProvider {

    /**
     * Returns IgUser object that represents a user with the specified username
     * or {@code null} if user with the specified username not found
     *
     * @param username the username of instagram user
     * @return IgUser object or {@code null} if user not found
     * @throws NullPointerException if the username is {@code null}
     * @throws IllegalArgumentException if the username is not valid
     */
    IgUser getInstagramUser(String username);
}
