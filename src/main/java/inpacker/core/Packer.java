package inpacker.core;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public interface Packer<T extends PackItem> {

    void pack(BlockingDeque<T> postsDeque,
              File packPath,
              Consumer<T> newItemSuccess,
              Consumer<T> newItemFail,
              Runnable done);
}
