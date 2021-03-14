package basics;


import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Stream1 {
    public static void main(String[] args) {

        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        System.out.println("Basico");
        myList.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
        System.out.println();

        System.out.println("Encontrar el primero de una lista");
        System.out.println("forma 1");
        Arrays.asList("a1", "a2", "a3")
                .stream()
                .findFirst()
                .ifPresent(System.out::println);
        System.out.println();

        System.out.println("forma 2");
        Stream.of("a1", "a2", "a3")
                .findFirst()
                .ifPresent(System.out::println);
        System.out.println();

        System.out.println("listar dentro de un rango...");
        IntStream.range(1, 4)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("funcion average en una lista...");
        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);
        System.out.println();

        System.out.println("funcion mapToInt (contar unicos) en una lista...");
        Stream.of("a1", "a2", "a3", "a4", "a5")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);
        System.out.println();

        System.out.println("funcion mapToObj  en una lista...");
        IntStream.range(0, 4)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
        System.out.println();

        System.out.println("funcion mapToObj en una lista con objetos de diferente tipo...");
        Stream.of(1.0, 2.0, 3.0)
                .mapToInt(Double::intValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
        System.out.println();
    }
}
