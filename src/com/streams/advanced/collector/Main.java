package advanced.collector;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static advanced.collector.Person.getPersons;

public class Main {
    public static void main(String[] args) {
        System.out.println("Filtrar Personas que comienzan con P:");
        List<Person> persons = getPersons();
        List<Person> filtered =
                persons
                        .stream()
                        .filter(p -> p.name.startsWith("P"))
                        .collect(Collectors.toList());

        System.out.println(filtered);
        System.out.println();


        System.out.println("Agrupar Personas por edad:");
        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));
        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));
        System.out.println();


        System.out.println("Promedio de edad:");
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));
        System.out.println(averageAge);
        System.out.println();


        System.out.println("Determina min, max y promedio de edad de una lista:");
        IntSummaryStatistics ageSummary =
                persons
                        .stream()
                        .collect(Collectors.summarizingInt(p -> p.age));
        System.out.println(ageSummary);
        System.out.println();


        System.out.println("Juntar las personas en una oracion:");
        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));
        System.out.println(phrase);
        System.out.println();


        System.out.println("supplier StringJoiner + accumulator + combiner + finisher");
        Collector<Person, StringJoiner, String> personNameCollector =
                Collector.of(
                        () -> new StringJoiner(" | "),          // supplier
                        (j, p) -> j.add(p.name.toUpperCase()),  // accumulator
                        (j1, j2) -> j1.merge(j2),               // combiner
                        StringJoiner::toString);                // finisher
        String names = persons
                .stream()
                .collect(personNameCollector);
        System.out.println(names);  // MAX | PETER | PAMELA | DAVID


    }
}
