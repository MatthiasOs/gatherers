package nullsafe;

import java.util.function.Function;
import java.util.stream.Stream;

public final class NullSafeUtil {
    private NullSafeUtil() {}

    public static <T, R> Function<T, Stream<R>> mapNullSafe(Function<T, R> mapper) {
        return t -> Stream.ofNullable(mapper.apply(t));
    }


}
