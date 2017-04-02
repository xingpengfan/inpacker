package inpacker.core;

import inpacker.core.model.InstagramPost;

import java.io.File;
import java.util.concurrent.BlockingDeque;
import java.util.function.Consumer;

public interface Packer {

    void pack(BlockingDeque<InstagramPost> postsDeque,
              File packPath,
              Consumer<InstagramPost> newItemSuccess,
              Consumer<InstagramPost> newItemFail,
              Runnable done);
}
