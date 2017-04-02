package inpacker.core;

import inpacker.core.model.IgPackConfig;
import inpacker.core.model.Pack;
import inpacker.core.model.PackSettings;

import java.io.File;
import java.util.List;

public interface Service {

    void createPack(IgPackConfig conf);

    File getPackFile(String packName);

    Pack getPack(String packName);

    List<Pack> getPacks();

    String getPackName(String username, PackSettings packSettings);

    String getPackName(IgPackConfig conf);
}
