package app.core;

public interface InpackerService {

    User getUser(String username);

    void createPack(String username, boolean includeImages, boolean includeVideos);
}
