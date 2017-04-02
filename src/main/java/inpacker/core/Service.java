package inpacker.core;

import inpacker.instagram.IgPackConfig;
import inpacker.instagram.Pack;

import java.io.File;
import java.util.List;

public interface Service {

    void createPack(IgPackConfig conf);

    File getPackFile(String packName);

    Pack getPack(String packName);

    List<Pack> getPacks();

    String getPackName(IgPackConfig conf);
}
