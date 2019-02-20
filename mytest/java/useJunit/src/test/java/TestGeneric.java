import org.junit.Assert;
import org.junit.Test;
import java.io.Serializable;

class Pair<K,V> implements Serializable {
    private K key;
    public K getKey() { return key; }
    private V value;
    public V getValue() { return value; }
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    @Override
    public String toString() {
        return key + "=" + value;
    }
    @Override
    public int hashCode() {
        return key.hashCode() * 13 + (value == null ? 0 : value.hashCode());
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Pair) {
            Pair pair = (Pair) o;
            if (key != null ? !key.equals(pair.key) : pair.key != null) return false;
            if (value != null ? !value.equals(pair.value) : pair.value != null) return false;
            return true;
        }
        return false;
    }
}

/**
 * @author liming.gong
 */
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
