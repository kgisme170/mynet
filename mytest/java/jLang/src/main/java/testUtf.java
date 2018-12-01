import java.io.UnsupportedEncodingException;

public class testUtf {
    public static void main(String[] args) {
        try {
            String h = "hello";
            System.out.println(h.length());
            System.out.println(h.getBytes().length);
            System.out.println(h.codePointCount(0, h.length()));
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").length());
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").length());
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);
            System.out.println();

            String h1 = "你好哦!";
            System.out.println(h1.length());
            System.out.println(h1.getBytes().length);
            System.out.println(h1.codePointCount(0, h1.length()));
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").length());
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").length());
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);
            System.out.println();

            String h2 = "hello实验";
            System.out.println(h2.length()); //7
            System.out.println(h2.getBytes().length); //11
            System.out.println(h2.codePointCount(0,h2.length())); //7
            System.out.println(new String(h2.getBytes("UTF-8"), "UTF-8").length());//7
            System.out.println(new String(h2.getBytes("UTF-16"), "UTF-16").length());//7
            System.out.println(new String(h2.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);//11
            System.out.println(new String(h2.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);//16

            System.out.println();
            System.out.println("水".getBytes().length);//3
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}