package inpacker.core;

import java.util.Collection;

public interface ItemRepository<C extends PackConfig<I>, I extends PackItem> {

    void getPackItems(C config, Collection<PackItem> items);

}
