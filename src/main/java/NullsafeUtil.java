import java.util.function.Function;
import java.util.stream.Stream;

public final class NullsafeUtil {
    private NullsafeUtil() {

    }

    public static <T, R> Function<T, Stream<R>> mapNullSafe(Function<T, R> mapper) {
        return t -> Stream.ofNullable(mapper.apply(t));
    }


}
