import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestParallel {
    @Test
    public void Sort() {
        String[] strings = new String[]{"xyz", "abc", "ok"};
        Arrays.parallelSort(strings);
        Arrays.asList(strings).forEach(System.out::println);

        int idx1 = Arrays.binarySearch(strings, "ok");
        System.out.println(idx1);
        int idx2 = Arrays.binarySearch(strings, "yes");
        System.out.println(idx2);

        Assert.assertEquals(1, idx1);
        Assert.assertTrue(0 > idx2);
    }
}