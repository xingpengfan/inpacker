package inpacker.core;

import java.util.function.Predicate;

public interface PackConfig<T extends PackItem> extends Predicate<T> {

    String getPackName();
}
