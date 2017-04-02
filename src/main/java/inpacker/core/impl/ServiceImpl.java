package inpacker.core.impl;

import inpacker.core.Service;
import inpacker.core.model.IgPackConfig;
import inpacker.core.model.InstagramPost;
import inpacker.core.Packer;
import inpacker.core.model.Pack;
import inpacker.core.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ServiceImpl implements Service {

    private final Repository repository;
    private final Packer        packer;

    private final List<Pack> packs;

    @Value("${packs.dir.path}")
    private String packsDirPath;

    @Value("${max.items.amount}")
    private int maxItemsAmount;

    private File packsDir;

    @Autowired
    public ServiceImpl(Repository repository, Packer packer) {
        this.repository = repository;
        this.packer = packer;
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
    public void createPack(IgPackConfig conf) {
        BlockingDeque<InstagramPost> itemsDeque = new LinkedBlockingDeque<>();
        new Thread(() -> repository.getInstagramPosts(conf, itemsDeque)).start();
        final String packName = getPackName(conf);
        Pack pack = new Pack(packName);
        packs.add(pack);
        packer.pack(itemsDeque, new File(packsDir, packName + ".zip"),
                p -> pack.newItem(), p -> {}, () -> {});
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
    public String getPackName(IgPackConfig conf) {
        return conf.username + "_" + conf.hashCode();
    }
}
