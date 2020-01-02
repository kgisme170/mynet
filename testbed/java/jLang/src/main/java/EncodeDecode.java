import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.*;
/**
 * @author liming.gong
 */
public class EncodeDecode {
    public static void main(String[] args) {
        Charset charset = StandardCharsets.UTF_8;
        //注意: StandardCharsets是jdk1.7添加的

        //从字符集中创建相应的编码和解码器
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        //构造一个buffer
        CharBuffer charBuffer = CharBuffer.allocate(64);
        charBuffer.put('你');
        charBuffer.put('好');
        charBuffer.put('!');
        charBuffer.flip();

        try {
            //将字符序列转换成字节序列
            ByteBuffer bb = encoder.encode(charBuffer);
            for (; bb.hasRemaining(); ) {
                System.out.print(bb.get() + " ");
            }

            //将字节序列转换成字符序列
            bb.flip();
            CharBuffer cb = decoder.decode(bb);
            System.out.println("\n" + cb);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }
}