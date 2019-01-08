import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;
public class TestGeneric {
    public static <T> void print(T t) {System.out.println(t);}
    public static <T> Pair<T, T> makePair(T t) {
        return new Pair<>(t, t);
    }
    @Test
    public void Use() {
        print("abc");
        print(5);
        Pair p = makePair(3);
        Assert.assertEquals(3, p.getKey());
        Assert.assertEquals(3, p.getValue());
    }
}
