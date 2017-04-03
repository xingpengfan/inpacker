package inpacker.core;

import java.util.concurrent.BlockingDeque;

public interface Repository<T extends PackConfig, R extends PackItem> {

    void getInstagramPosts(T config, BlockingDeque<R> deque);

}
