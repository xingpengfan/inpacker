package inpacker.core;

import java.util.concurrent.BlockingDeque;

public interface Repository<C extends PackConfig<I>, I extends PackItem> {

    void getPackItems(C config, BlockingDeque<I> deque);

}
