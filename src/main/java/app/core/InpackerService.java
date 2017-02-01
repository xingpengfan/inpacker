package app.core;

import app.core.model.User;

import java.io.File;

public interface InpackerService {

    User getUser(String username);

    void createPack(String username, boolean includeImages, boolean includeVideos);

    File getPackFile(String username);
}
