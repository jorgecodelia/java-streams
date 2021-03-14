package advanced.parallelStream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static advanced.parallelStream.Person.getPersons;

public class Main {
    public static void main(String[] args) {
        /*
        Streams can be executed in parallel to increase runtime performance on large amount of input elements.
        Parallel streams use a common ForkJoinPool available via the static ForkJoinPool.commonPool() method.
        The size of the underlying thread-pool uses up to five threads - depending on the amount
        of available physical CPU cores.

        This value can be decreased or increased by setting the following JVM parameter:
        -Djava.util.concurrent.ForkJoinPool.common.parallelism=5
         */
        System.out.println("muestra la cantidad de hilos de tu maquina local:");
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());
        System.out.println();



        /*
        Collections support the method parallelStream() to create a parallel stream of elements.
        Alternatively you can call the intermediate method parallel() on a given stream to convert a sequential
        stream to a parallel counterpart.
        In order to understate the parallel execution behavior of a parallel stream the next example
        The output may differ in consecutive runs because the behavior which particular
        thread is actually used is non-deterministic.

        prints information about the current thread to sout:
         */
        System.out.println("Ejemplo de parallel Stream:");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
        System.out.println();


        /*
        It seems that sort is executed sequentially on the main thread only.
        Actually, sort on a parallel stream uses the new Java 8 method Arrays.parallelSort() under the hood.
        As stated in Javadoc this method decides on the length of the array if sorting will be performed sequentially or in parallel:

        If the length of the specified array is less than the minimum granularity,
        then it is sorted using the appropriate Arrays.sort method.
         */
        System.out.println("Mismo ejemplo anterior pero ordenado:");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
        System.out.println();


        /*
        The result output reveals that both the accumulator and the combiner
        functions are executed in parallel on all available threads
         */
        System.out.println("ParallelStream con reduce:");
        List<Person> persons = getPersons();
        persons
                .parallelStream()
                .reduce(0,
                        (sum, p) -> {
                            System.out.format("accumulator: sum=%s; person=%s [%s]\n",
                                    sum, p, Thread.currentThread().getName());
                            return sum += p.age;
                        },
                        (sum1, sum2) -> {
                            System.out.format("combiner: sum1=%s; sum2=%s [%s]\n",
                                    sum1, sum2, Thread.currentThread().getName());
                            return sum1 + sum2;
                        });
        System.out.println();
    }
}
