package core;

import java.util.Collection;

public interface Repository<C extends PackConfig<I>, I extends PackItem> {

    void getPackItems(C config, Collection<I> deque);

}
