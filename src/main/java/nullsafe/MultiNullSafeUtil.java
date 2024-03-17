package nullsafe;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class MultiNullSafeUtil {
    private MultiNullSafeUtil() {}

    public static <T, R> BiConsumer<T, Consumer<R>> mapNullSafe(Function<T, R> mapper) {
        return (t, downstream) -> {
            R result = mapper.apply(t);
            if (result != null) {
                downstream.accept(result);
            }
        };
    }
}
