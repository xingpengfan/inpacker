package inpacker.core;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public interface Packer {

    void pack(BlockingDeque<PackItem> itemsDeque,
              File packsDir,
              String packId,
              Consumer<PackItem> newItemSuccess,
              Consumer<PackItem> newItemFail,
              Consumer<File> done,
              Runnable failed);

    default void pack(BlockingDeque<PackItem> itemsDeque, File packDir, Pack pack) {
        pack(itemsDeque, packDir, pack.getId(),
            pack::newSuccessItem,
            pack::newFailedItem,
            pack::done,
            pack::failed);
    }
}
