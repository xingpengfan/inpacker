package inpacker.core;

import java.util.Collection;

public interface ItemRepository<C extends PackConfig> {

    void getPackItems(C config, Collection<PackItem> items);

}
