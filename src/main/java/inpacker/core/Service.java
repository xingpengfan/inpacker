package inpacker.core;

import inpacker.core.model.Pack;
import inpacker.core.model.PackSettings;

import java.io.File;
import java.util.List;

public interface Service {

    void createPack(String username, PackSettings packSettings);

    File getPackFile(String packName);

    Pack getPack(String packName);

    List<Pack> getPacks();

    String getPackName(String username, PackSettings packSettings);
}
