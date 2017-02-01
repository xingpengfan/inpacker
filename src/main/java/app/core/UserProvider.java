package app.core;

import app.core.model.User;

public interface UserProvider {

    /**
     * Returns User object that represents a user with the specified username
     * or {@code null} if user with the specified username not found
     *
     * @param username the username of instagram user
     * @return User object or {@code null} if user not found
     * @throws NullPointerException if the username is {@code null}
     * @throws IllegalArgumentException if the username is not valid
     */
    User getUser(String username);
}
