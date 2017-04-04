package inpacker.core;

import java.io.File;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class DefaultPackService<C extends PackConfig<I>, I extends PackItem> {

    private final Repository<C, I> repo;
    private final Packer<I> packer;
    private final Map<String, Pack> packs;
    private final File packsDir;

    public DefaultPackService(File packsDirectory, Repository<C, I> repository) {
        repo = repository;
        packsDir = packsDirectory;
        packsDir.mkdirs();
        packer = new ZipPacker<>();
        packs = new ConcurrentHashMap<>();
    }

    public String createPack(C config) {
        final String packName = config.getPackName();
        if (packs.get(packName) != null) return packName;
        final Pack pack = new Pack();
        packs.put(packName, pack);
        final BlockingDeque<I> deque = new LinkedBlockingDeque<>();
        new Thread(() -> repo.getPackItems(config, deque)).start();
        pack.processing();
        new Thread(() -> packer.pack(deque, packsDir, packName,
                pack::newSuccessItem,
                pack::newFailedItem,
                pack::done,
                pack::failed)).start();
        return packName;
    }

    public Pack getPack(String packName) {
        return packs.get(packName);
    }

    public File getPackFile(String packName) {
        final Pack pack = packs.get(packName);
        if (pack == null || !pack.isDone()) return null;
        else return new File(packsDir, packName + ".zip");
    }
}
