package inpacker.core;

public interface PackService<C extends PackConfig<I>, I extends PackItem> {

    Pack createPack(C packConfig);

    Pack getPack(String packId);

    java.io.File getPackFile(String packId);
}
