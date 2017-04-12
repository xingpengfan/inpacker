package core;

import java.util.function.Predicate;

public interface PackConfig<I extends PackItem> extends Predicate<I> {

    @Override
    boolean test(I item);

    String getUniqueId();
}
