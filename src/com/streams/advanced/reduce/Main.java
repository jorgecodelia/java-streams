package advanced.reduce;

import java.util.List;
import static advanced.reduce.Person.getPersons;

public class Main {
    public static void main(String[] args) {
        //The reduction operation combines all elements of the stream into a single result
        System.out.println("Determina la persona de más edad:");
        List<Person> persons = getPersons();
        persons
                .stream()
                .reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(System.out::println);
        System.out.println();

        //The second reduce method accepts both an identity value and a BinaryOperator accumulator
        System.out.println("Combina todos los elementos de una lista en un nuevo objeto:");
        Person result =
                persons
                        .stream()
                        .reduce(new Person("", 0), (p1, p2) -> {
                            p1.age += p2.age;
                            p1.name += p2.name;
                            return p1;
                        });
        System.out.format("name=%s; age=%s", result.name, result.age);
        System.out.println();
        System.out.println();


        //The third reduce method accepts three parameters: an identity value, a BiFunction accumulator and a combiner function of type BinaryOperator.
        System.out.println("Suma todas las edades con el metodo reduce:");
        Integer ageSum = persons
                .stream()
                .reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);
        System.out.println(ageSum);
        System.out.println();

        //to Debug the last output
        System.out.println("debug del ultimo método");
        ageSum = persons
                .stream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s\n", sum, p);
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s\n", sum1, sum2);
                            return sum1 + sum2;
                        });
        System.out.println(ageSum);
        System.out.println();
    }
}
