package app.core.impl;

import app.core.InpackerService;
import app.core.model.Item;
import app.core.Packer;
import app.core.model.PackSettings;
import app.core.model.User;
import app.core.UserMediaProvider;
import app.core.UserProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class InpackerServiceImpl implements InpackerService {

    private final UserMediaProvider mediaProvider;
    private final Packer            packer;
    private final UserProvider      userProvider;

    private final Map<String, Boolean> packs;

    @Value("${packs.dir.path}")
    private String packsDirPath;

    @Value("${max.items.amount}")
    private int maxItemsAmount;

    private File packsDir;

    @Autowired
    public InpackerServiceImpl(UserMediaProvider userMediaProvider, Packer packer, UserProvider userProvider) {
        this.mediaProvider = userMediaProvider;
        this.packer = packer;
        this.userProvider = userProvider;
        packs = new ConcurrentHashMap<>();
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
        packs.put(packName, false);
        packer.pack(itemsDeque, new File(packsDir, packName + ".zip"), packSettings);
        packs.put(packName, true);
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
    public Boolean getPackStatus(String packName) {
        return packs.get(packName);
    }

    @Override
    public Map<String, Boolean> getPacks() {
        return packs;
    }

    @Override
    public String getPackName(String username, PackSettings packSettings) {
        return username + "_" + packSettings.hashCode();
    }
}
