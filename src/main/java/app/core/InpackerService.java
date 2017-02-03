package app.core;

import app.core.model.PackSettings;
import app.core.model.User;

import java.io.File;

public interface    InpackerService {

    User getUser(String username);

    void createPack(String username, PackSettings packSettings);

    File getPackFile(String packName);

    Boolean getPackStatus(String packName);

    String getPackName(String username, PackSettings packSettings);
}
