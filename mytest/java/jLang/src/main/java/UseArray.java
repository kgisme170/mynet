import java.lang.reflect.Array;
import java.util.Arrays;

public class UseArray {
    public static void main(String [] args) {
        int[] a = new int[]{4, 1, 2, 3};
        int[] a2 = Arrays.copyOf(a, a.length);
        Arrays.sort(a2);
        System.out.println(Arrays.toString(a));

        int[][] a3 = (int[][]) Array.newInstance(a.getClass(), a.length);
        int[] a4 = (int[]) Array.newInstance(int.class, a.length);
        System.out.println(a3);
        System.out.println(a4);
    }
}
