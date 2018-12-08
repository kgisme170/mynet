import java.io.UnsupportedEncodingException;

public class TestUtf {
    public static void main(String[] args) {
        try {
            // new String(getBytes("UTF-8"), "UTF-8") 等于什么也没有做
            // new String(getBytes("UTF-8"), "UTF-8") 先编码再解码，也等于什么也没做
            // getBytes("UTF-16") 会在字符串的最后面加上BOM，两个字节
            String h = "hello";
            System.out.println(h.length());//5
            System.out.println(h.getBytes().length);//5
            System.out.println(h.codePointCount(0, h.length()));//5
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").length());//5
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").length());//5
            System.out.println(new String(h.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);//5
            System.out.println(new String(h.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);//12
            System.out.println();

            String h1 = "你好哦!";
            System.out.println(h1.length());//4
            System.out.println(h1.getBytes().length);// UTF16编码后 10 字节
            System.out.println(h1.codePointCount(0, h1.length()));
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").length());//4
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").length());//4
            System.out.println(new String(h1.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);//4
            System.out.println(new String(h1.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);//10
            System.out.println();

            String h2 = "hello实验";
            System.out.println(h2.length()); //7 字数
            System.out.println(h2.getBytes().length); //11 = 5+4+2(BOM)
            System.out.println(h2.codePointCount(0,h2.length())); //7
            System.out.println(new String(h2.getBytes("UTF-8"), "UTF-8").length());//7
            System.out.println(new String(h2.getBytes("UTF-16"), "UTF-16").length());//7
            System.out.println(new String(h2.getBytes("UTF-16"), "UTF-16").getBytes("UTF-8").length);//11
            System.out.println(new String(h2.getBytes("UTF-8"), "UTF-8").getBytes("UTF-16").length);//16 = 10 + 4 + 2

            System.out.println();
            System.out.println("水".getBytes().length);//3
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}