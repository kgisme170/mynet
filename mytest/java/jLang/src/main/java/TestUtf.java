import java.io.UnsupportedEncodingException;
/**
 * @author liming.gong
 */
public class TestUtf {
    public static void main(String[] args) {
        try {
            // new String(getBytes("UTF-8"), "UTF-8") 等于什么也没有做
            // new String(getBytes("UTF-8"), "UTF-8") 先编码再解码，也等于什么也没做
            // getBytes("UTF-16") 会在字符串的最后面加上BOM，两个字节
            /*
                5
                5
                5
                5
                5
                5
                12
             */
            String h = "hello";
            System.out.println(h.length());
            System.out.println(h.getBytes().length);
            System.out.println(h.codePointCount(0, h.length()));
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").length());
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").length());
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);
            System.out.println();

            /*
                4
                10
                4
                4
                4
                10
                10
             */
            String h1 = "你好哦!";
            System.out.println(h1.length());
            System.out.println(h1.getBytes().length);
            System.out.println(h1.codePointCount(0, h1.length()));
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").length());
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").length());
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);
            System.out.println();

            /*
                7
                11
                7
                7
                7
                11
                16
             */
            String h2 = "hello实验";
            System.out.println(h2.length());
            System.out.println(h2.getBytes().length);
            System.out.println(h2.codePointCount(0,h2.length()));
            System.out.println(new String(h2.getBytes("UTF-8"), "UTF-8").length());
            System.out.println(new String(h2.getBytes("UTF-16"), "UTF-16").length());
            System.out.println(new String(h2.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);
            System.out.println(new String(h2.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);

            System.out.println();

            //3
            System.out.println("水".getBytes().length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}