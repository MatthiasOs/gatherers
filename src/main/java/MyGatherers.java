import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Gatherer;

public class MyGatherers {

    public static <T, R> Gatherer<T, ?, R> mapNullSafe(Function<T, R> mapper) {
        return Gatherer.of((_, element, downstream) -> {
            R mappedElement = mapper.apply(element);
            if (mappedElement != null) {
                downstream.push(mappedElement);
            }
            return true;
        });
    }

    public static <T> Gatherer<T, ?, T> keepOnly(Predicate<T> filter) {
        return Gatherer.of((_, element, downstream) -> {
            if (filter.test(element)) {
                downstream.push(element);
            }
            return true;
        });
    }

    public static <T, R> Gatherer<T, ?, R> instanceOf(Class<R> targetClass) {
        return Gatherer.of((_, element, downstream) -> {
            if (targetClass.isInstance(element)) {
                downstream.push(targetClass.cast(element));
            }
            return true;
        });
    }
}
