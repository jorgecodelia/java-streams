package advanced.Optional;


import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        //In order to resolve the inner string foo of an outer instance you have to add multiple null checks to prevent possible NullPointerExceptions
        System.out.println("Call to flatMap returns an Optional wrapping the desired object if present or null if absent");
        Outer outer = new Outer();
        if (outer != null && outer.nested != null && outer.nested.inner != null) {
            System.out.println(outer.nested.inner.foo);
        }
        Optional.of(new Outer())
                .flatMap(o -> Optional.ofNullable(o.nested))
                .flatMap(n -> Optional.ofNullable(n.inner))
                .flatMap(i -> Optional.ofNullable(i.foo))
                .ifPresent(System.out::println);
        System.out.println();



    }
}
