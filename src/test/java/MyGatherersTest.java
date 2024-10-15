import data.Adress;
import data.Data;
import data.Employee;
import data.Name;
import nullsafe.MultiNullSafeUtil;
import nullsafe.NullSafeUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("preview")
public class MyGatherersTest {
    private final List<Employee> employees = List.of(
            new Employee(null),
            new Employee(new Name(null)),
            new Employee(new Name("asd1")),
            new Employee(null),
            new Employee(new Name("asd2")),
            new Employee(new Name(null)));

    @Test
    void withMapShouldThrowNPE() {
        Assertions.assertThatNullPointerException().isThrownBy(() ->
                employees.stream()
                         .map(Employee::data)
                         .map(Name::title)
                         .map(String::toUpperCase)
                         .toList());
    }

    @Test
    void withGathererShouldThrowNoException() {
        List<String> employeeTitleUppercase = employees.stream()
                                                       .gather(MyGatherers.mapNullSafe(Employee::data))
                                                       .gather(MyGatherers.mapNullSafe(Name::title))
                                                       .gather(MyGatherers.mapNullSafe(String::toUpperCase))
                                                       .toList();
        Assertions.assertThat(employeeTitleUppercase).containsExactlyInAnyOrder("ASD1", "ASD2");
    }


    @Test
    void withNullSafeStreamMethod() {
        List<String> employeeTitleUppercase = employees.stream()
                                                       .flatMap(NullSafeUtil.mapNullSafe(Employee::data))
                                                       .flatMap(NullSafeUtil.mapNullSafe(Name::title))
                                                       .flatMap(NullSafeUtil.mapNullSafe(String::toUpperCase))
                                                       .toList();
        Assertions.assertThat(employeeTitleUppercase).containsExactlyInAnyOrder("ASD1", "ASD2");
    }

    @Test
    void withNullSafeStreamMethodMapMulti() {
        List<String> employeeTitleUppercase = employees.stream()
                                                       .mapMulti(MultiNullSafeUtil.mapNullSafe(Employee::data))
                                                       .mapMulti(MultiNullSafeUtil.mapNullSafe(Name::title))
                                                       .mapMulti(MultiNullSafeUtil.mapNullSafe(String::toUpperCase))
                                                       .toList();
        Assertions.assertThat(employeeTitleUppercase).containsExactlyInAnyOrder("ASD1", "ASD2");
    }

    @Test
    void keepOnlyShouldFilter() {
        List<String> onlyEmployeesWith2InTitle = employees.stream()
                                                          .gather(MyGatherers.keepOnly(e -> e.data() != null))
                                                          .map(Employee::data)
                                                          .gather(MyGatherers.keepOnly(n -> n.title() != null))
                                                          .map(Name::title)
                                                          .filter(Objects::nonNull)
                                                          .gather(MyGatherers.keepOnly(t -> t.contains("2")))
                                                          .toList();
        Assertions.assertThat(onlyEmployeesWith2InTitle).singleElement().isEqualTo("asd2");
    }

    @Test
    void instanceOfShouldCast() {
        List<EmployeeWithData> persons = List.of(
                new EmployeeWithData(new Name("title")),
                new EmployeeWithData(new Adress("adress")));
        List<Adress> employeesCasted = persons.stream()
                                              .map(EmployeeWithData::data)
                                              .gather(MyGatherers.instanceOf(Adress.class))
                                              .toList();
        Assertions.assertThat(employeesCasted).singleElement().isInstanceOf(Adress.class);
    }

    private record EmployeeWithData(Data data) {

    }
}
