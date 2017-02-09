package app.core.impl;

import app.core.Inpacker;
import app.core.model.Item;
import app.core.Packer;
import app.core.model.Pack;
import app.core.model.PackSettings;
import app.core.model.User;
import app.core.UserMediaProvider;
import app.core.UserProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class InpackerImpl implements Inpacker {

    private final UserMediaProvider mediaProvider;
    private final Packer            packer;
    private final UserProvider      userProvider;

    private final List<Pack> packs;

    @Value("${packs.dir.path}")
    private String packsDirPath;

    @Value("${max.items.amount}")
    private int maxItemsAmount;

    private File packsDir;

    @Autowired
    public InpackerImpl(UserMediaProvider userMediaProvider, Packer packer, UserProvider userProvider) {
        this.mediaProvider = userMediaProvider;
        this.packer = packer;
        this.userProvider = userProvider;
        packs = new ArrayList<>();
    }

    @PostConstruct
    private void createPacksDir() {
        packsDir = new File(packsDirPath);
        if (!packsDir.exists()) {
            packsDir.mkdirs();
        }
    }

    @Override
    public User getUser(String username) {
        return userProvider.getUser(username);
    }

    @Override
    public void createPack(String username, PackSettings packSettings) {
        BlockingDeque<Item> itemsDeque = new LinkedBlockingDeque<>();
        new Thread(() -> mediaProvider.getUserMedia(username, itemsDeque, packSettings, maxItemsAmount))
                .start();
        final String packName = getPackName(username, packSettings);
        Pack pack = new Pack(packName);
        packs.add(pack);
        packer.pack(itemsDeque, new File(packsDir, packName + ".zip"), packSettings, pack::newItem);
        pack.ready();
    }

    @Override
    public File getPackFile(String packName) {
        File packFile = new File(packsDirPath + "/" + packName + ".zip");
        if (packFile.exists())
            return packFile;
        else
            return null;
    }

    @Override
    public Pack getPack(String packName) {
        for (Pack pack : packs) {
            if (pack.getName().equals(packName))
                return pack;
        }
        return null;
    }

    @Override
    public List<Pack> getPacks() {
        return packs;
    }

    @Override
    public String getPackName(String username, PackSettings packSettings) {
        return username + "_" + packSettings.hashCode();
    }
}
