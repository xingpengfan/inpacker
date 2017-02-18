package app.core;

import app.core.model.Pack;
import app.core.model.PackSettings;

import java.io.File;
import java.util.List;

public interface Inpacker {

    void createPack(String username, PackSettings packSettings);

    File getPackFile(String packName);

    Pack getPack(String packName);

    List<Pack> getPacks();

    String getPackName(String username, PackSettings packSettings);
}
