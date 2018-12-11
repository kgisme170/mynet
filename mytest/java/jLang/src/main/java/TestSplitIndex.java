/**
 * @author liming.gong
 */
public class TestSplitIndex {
    public static void main(String [] args) {
        String str = "a,b,c,,";
        String[] ary = str.split(",");
        // 预期大于3，结果是3
        System.out.println(ary.length);

        String str2 = "a,b,c,d,";
        String[] ary2 = str2.split(",");
        System.out.println(ary2.length);

        String str3 = "a,b,c,d,e";
        String[] ary3 = str3.split(",");
        System.out.println(ary3.length);
    }
}