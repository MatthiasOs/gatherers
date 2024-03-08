import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GatherersTest {
    private final List<Employee> employees = List.of(
            new Employee(null),
            new Employee(new NameData(null)),
            new Employee(new NameData("asd")));

    @Test
    void withMapShouldThrowNPE() {
        Assertions.assertThatNullPointerException().isThrownBy(() ->
                employees.stream()
                         .map(Employee::data)
                         .map(NameData::title)
                         .map(String::toUpperCase)
                         .toList());
    }

    @Test
    void withGathererShouldThrowNoException() {
        List<String> employeeTitleUppercase = employees.stream()
                                                       .gather(Gatherers.mapNullSafe(Employee::data))
                                                       .gather(Gatherers.mapNullSafe(NameData::title))
                                                       .gather(Gatherers.mapNullSafe(String::toUpperCase))
                                                       .toList();
        Assertions.assertThat(employeeTitleUppercase).singleElement().isEqualTo("ASD");
    }


    @Test
    void withNullSafeStreamMethod() {
        List<String> employeeTitleUppercase = employees.stream()
                                                       .flatMap(NullsafeUtil.mapNullSafe(Employee::data))
                                                       .flatMap(NullsafeUtil.mapNullSafe(NameData::title))
                                                       .flatMap(NullsafeUtil.mapNullSafe(String::toUpperCase))
                                                       .toList();
        Assertions.assertThat(employeeTitleUppercase).singleElement().isEqualTo("ASD");
    }

    @Test
    void withNullSafeStreamMethodMapMulti() {
        List<String> employeeTitleUppercase = employees.stream()
                                                       .<NameData>mapMulti(NullsafeUtil.multiNullSafe(Employee::data))
                                                       .<String>mapMulti(NullsafeUtil.multiNullSafe(NameData::title))
                                                       .<String>mapMulti(NullsafeUtil.multiNullSafe(String::toUpperCase))
                                                       .toList();
        Assertions.assertThat(employeeTitleUppercase).singleElement().isEqualTo("ASD");
    }
}
