package core;

import java.io.File;
import java.util.Map;
import java.util.concurrent.*;

public class DefaultPackService<C extends PackConfig<I>, I extends PackItem> implements PackService<C, I> {

    private final Repository<C, I> repository;
    private final Packer<I> packer;
    private final Map<String, Pack> packs;
    private final File packsDir;
    private final ExecutorService executorService;

    public DefaultPackService(File packsDirectory, Repository<C, I> repository, Packer<I> packer) {
        packs = new ConcurrentHashMap<>();
        this.repository = repository;
        this.packer = packer;
        packsDir = packsDirectory;
        packsDir.mkdirs();
        this.executorService = Executors.newCachedThreadPool();
    }

    public Pack createPack(C config) {
        final String id = config.getUniqueId();
        if (packs.containsKey(id)) return packs.get(id);
        final Pack pack = new Pack(id);
        packs.put(id, pack);
        final BlockingDeque<I> deque = new LinkedBlockingDeque<>();
        executorService.submit(() -> repository.getPackItems(config, deque));
        pack.processing();
        executorService.submit(() -> packer.pack(deque, packsDir, pack));
        return pack;
    }

    public Pack getPack(String packId) {
        return packs.get(packId);
    }
}
