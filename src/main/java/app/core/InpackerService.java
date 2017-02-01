package app.core;

import app.core.model.User;

public interface InpackerService {

    User getUser(String username);

    void createPack(String username, boolean includeImages, boolean includeVideos);
}
