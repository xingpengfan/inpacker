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
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class InpackerServiceImpl implements InpackerService {

    private final UserMediaProvider mediaProvider;
    private final Packer            packer;
    private final UserProvider userProvider;

    @Value("${packs.dir.path}")
    private String packsDirPath;

    @Value("${max.items.amount}")
    private int maxItemsImount;

    private File packsDir;

    @Autowired
    public InpackerServiceImpl(UserMediaProvider userMediaProvider, Packer packer, UserProvider userProvider) {
        this.mediaProvider = userMediaProvider;
        this.packer = packer;
        this.userProvider = userProvider;
    }

    @PostConstruct
    private boolean createPacksDir() {
        packsDir = new File(packsDirPath);
        return !packsDir.exists() && packsDir.mkdirs();
    }

    @Override
    public User getUser(String username) {
        return userProvider.getUser(username);
    }

    @Override
    public void createPack(String username, PackSettings packSettings) {
        BlockingDeque<Item> itemsDeque = new LinkedBlockingDeque<>();

        Predicate<Item> itemsFilter = getItemsFilter(packSettings);
        new Thread(() -> mediaProvider.getUserMedia(username, itemsDeque, itemsFilter, maxItemsImount)).start();

        packer.pack(itemsDeque, new File(packsDir, username + ".zip"), getItemNameCreator(packSettings));
    }

    @Override
    public File getPackFile(String username) {
        File packFile = new File(packsDirPath + "/" + username + ".zip");
        if (packFile.exists())
            return packFile;
        else
            return null;
    }

    private Predicate<Item> getItemsFilter(PackSettings ps) {
        return item -> item.isVideo() && ps.includeVideos || item.isImage() && ps.includeImages;
    }

    private BiFunction<Item, Integer, String> getItemNameCreator(PackSettings ps) {
        switch (ps.fileNamePattern) {
            case "numbers":
                return (item, index) -> index + fileNameExtension(item);
            default:
                return (item, index) -> item.id + fileNameExtension(item);
        }
    }

    private String fileNameExtension(Item item) {
        return item.isVideo() ? ".mp4" : item.isImage() ? ".jpg" : "";
    }
}
