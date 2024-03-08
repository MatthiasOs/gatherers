import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class NullsafeUtil {
    private NullsafeUtil() {

    }

    public static <T, R> Function<T, Stream<R>> mapNullSafe(Function<T, R> mapper) {
        return t -> Stream.ofNullable(mapper.apply(t));
    }

    public static <T, R> BiConsumer<? super T, ? super Consumer<R>> multiNullSafe(Function<T, R> mapper) {
        return (t, downstream) -> {
            R result = mapper.apply(t);
            if (result != null) {
                downstream.accept(result);
            }
        };
    }
}
