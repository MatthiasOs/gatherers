import java.util.function.Function;
import java.util.stream.Gatherer;

public class Gatherers {

    public static <T, R> Gatherer<T, ?, R> mapNullSafe(Function<T, R> mapper) {
        return Gatherer.of((state, element, downstream) -> {
            R mappedElement = mapper.apply(element);
            if (mappedElement != null) {
                downstream.push(mappedElement);
            }
            return true;
        });
    }
}
