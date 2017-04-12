package core;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public interface Packer<T extends PackItem> {

    void pack(BlockingDeque<T> itemsDeque,
              File packsDir,
              String packId,
              Consumer<T> newItemSuccess,
              Consumer<T> newItemFail,
              Consumer<File> done,
              Runnable failed);

    default void pack(BlockingDeque<T> itemsDeque, File packDir, Pack pack) {
        pack(itemsDeque, packDir, pack.getId(),
                pack::newSuccessItem,
                pack::newFailedItem,
                pack::done,
                pack::failed);
    }
}
