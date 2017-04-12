package core;

public interface PackService<C extends PackConfig<I>, I extends PackItem> {

    Pack createPack(C packConfig);

    Pack getPack(String packId);

    default java.io.File getPackFile(String packId) {
        final Pack pack = getPack(packId);
        return pack == null ? null : pack.getPackFile();
    }
}
