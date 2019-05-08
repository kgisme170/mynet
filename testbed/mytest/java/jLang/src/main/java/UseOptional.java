import java.util.Locale;
import java.util.Optional;
/**
 * @author liming.gong
 */
public class UseOptional {
    public static class User{
        private String name = "name";
        private int age = 20;
        String getUser(){return name;}
    }
    public static void f(Optional<User> u){
        u.map(User::getUser).map(name->name.toUpperCase()).ifPresent(System.out::println);
    }

    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }

    public static void main(String [] args){
        User u = new User();
        UseOptional.f(Optional.of(u));
        UseOptional.f(Optional.ofNullable(null));

        Optional<Double> d = Optional.of(-4.0).flatMap(UseOptional::inverse).flatMap(UseOptional::squareRoot);
        System.out.println();
        System.out.println(d);
        System.out.println(Locale.getDefault().getDisplayName());
    }
}
