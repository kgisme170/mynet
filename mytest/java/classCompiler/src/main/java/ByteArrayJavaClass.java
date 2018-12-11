import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @author liming.gong
 */
class ByteArrayJavaClass extends SimpleJavaFileObject {
    private ByteArrayOutputStream stream;

    public ByteArrayJavaClass(String className) {
        super(URI.create("bytes:///" + className), JavaFileObject.Kind.CLASS);
        stream = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() {
        return stream;
    }

    public byte[] getBytes() {
        return stream.toByteArray();
    }
}