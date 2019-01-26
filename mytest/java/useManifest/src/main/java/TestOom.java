/**
 * @author liming.gong
 */
public class TestOom {
    public static void f() {
        throw new RuntimeException("abc");
    }

    public static void main(String[] args) {
        System.out.println("ok");
        final int size = 2048;
        int[][] a = new int[size][];
        for (int i = 0; i < size; ++i) {
            try {
                a[i] = new int[size];
            } catch (OutOfMemoryError e) {
                System.out.println(i);
                System.exit(1);
            }
        }
        f();
    }
}