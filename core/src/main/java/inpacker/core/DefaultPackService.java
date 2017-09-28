package inpacker.core;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DefaultPackService<C extends PackConfig> implements PackService<C> {

    private final ItemRepository<C> repository;
    private final Packer packer;
    private final Map<String, Pack> packs;
    private final File packsDir;
    private final ExecutorService executorService;

    public DefaultPackService(File packsDirectory, ItemRepository<C> itemRepository, Packer packer) {
        requireNonNull(packsDirectory, "required non null packsDirectory");
        requireNonNull(itemRepository, "required nun null itemRepository");
        requireNonNull(packer, "required non null packer");
        final boolean exists = packsDirectory.exists();
        if (exists && !packsDirectory.isDirectory())
            throw new IllegalArgumentException("packsDirectory is not a directory");
        packs = new ConcurrentHashMap<>();
        this.repository = itemRepository;
        this.packer = packer;
        packsDir = packsDirectory;
        if (!exists && !packsDir.mkdirs())
            throw new RuntimeException("unable to create packsDirectory");
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public Pack createPack(C config) {
        requireNonNull(config, "required non null config");
        final String id = config.getUniqueId();
        if (packs.containsKey(id)) return packs.get(id);
        final Pack pack = new Pack(id, config.numberOfItems());
        packs.put(id, pack);
        final BlockingDeque<PackItem> deque = new LinkedBlockingDeque<>();
        executorService.submit(() -> repository.getPackItems(config, deque));
        pack.processing();
        executorService.submit(() -> packer.pack(deque, packsDir, pack));
        return pack;
    }

    @Override
    public Pack getPack(String packId) {
        return packs.get(packId);
    }

    @Override
    public List<Pack> getPacks(Predicate<Pack> filter) {
        return packs.values().stream().filter(filter).collect(Collectors.toList());
    }
}
