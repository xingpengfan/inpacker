package inpacker.core;

import java.util.List;
import java.util.function.Predicate;

public interface PackService<C extends PackConfig> {

    Pack createPack(C packConfig);

    Pack getPack(String packId);

    List<Pack> getPacks(Predicate<Pack> filter);

    default List<Pack> getPacks() {
        return getPacks(p -> true);
    }

    default java.io.File getPackFile(String packId) {
        final Pack pack = getPack(packId);
        return pack == null ? null : pack.getFile();
    }
}
