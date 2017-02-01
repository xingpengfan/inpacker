package app.core.impl;

import app.core.InpackerService;
import app.core.Item;
import app.core.Packer;
import app.core.UserMediaProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class InpackerServiceImpl implements InpackerService {

    private final UserMediaProvider mediaProvider;
    private final Packer            packer;

    @Value("${packs.dir.path}")
    private String packsDirPath;
    private File packsDir;

    @Autowired
    public InpackerServiceImpl(UserMediaProvider userMediaProvider, Packer packer) {
        this.mediaProvider = userMediaProvider;
        this.packer = packer;
    }

    @PostConstruct
    private boolean createPacksDir() {
        packsDir = new File(packsDirPath);
        return !packsDir.exists() && packsDir.mkdirs();
    }

    @Override
    public void createPack(String username, boolean includeImages, boolean includeVideos) {
        BlockingDeque<Item> itemsDeque = new LinkedBlockingDeque<>();

        new Thread(() -> mediaProvider.getUserMedia(username, itemsDeque)).start();

        packer.pack(itemsDeque, new File(packsDir, username), (item) -> item.id + (item.isVideo() ? ".mp4" : ".jpg"));
    }
}
