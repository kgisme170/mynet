import java.util.ArrayList;

public class UseSafeVarargs {
    @SafeVarargs
    public static <T> T useVarargs(T... args) {
        System.out.println(args.length);
        return args.length > 0 ? args[0] : null;
    }

    public static void main(String[] args) {
        System.out.println(useVarargs(new ArrayList<String>()));
    }
}