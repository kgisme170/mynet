import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;

class StringBuilderJavaSource extends SimpleJavaFileObject {
    private StringBuilder code;

    public StringBuilderJavaSource(String className) {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        code = new StringBuilder();
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        System.out.println(code);
        return code;
    }

    public void append(String s) {
        code.append(s);
        code.append('\n');
    }
}

class ByteArrayJavaClass extends SimpleJavaFileObject {
    private ByteArrayOutputStream stream;

    public ByteArrayJavaClass(String className) {
        super(URI.create("bytes:///" + className), Kind.CLASS);
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

class MapClassLoader extends ClassLoader {
    private Map<String, byte[]> classes;
    public MapClassLoader(Map<String, byte[]> classes) {
        this.classes = classes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = classes.get(name);
        if (classBytes == null) {
            throw new ClassNotFoundException(name);
        }

        Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
        if (cl == null) {
            throw new ClassNotFoundException(name);
        }
        return cl;
    }
}
/**
 * @author liming.gong
 */
public class UseCompiler2 {
    public final static String CLASS_PREFIX = "x.";
    public static void main(String [] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        JavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(
                    Location location,
                    final String className,
                    JavaFileObject.Kind kind,
                    FileObject sibling) throws IOException {
                System.out.println("Enter getJavaFileForOutput");
                if (className.startsWith(CLASS_PREFIX)) {
                    ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
                    classFileObjects.add(fileObject);
                    System.out.println("Enter x");
                    return fileObject;
                } else {
                    System.out.println("Enter others");
                    return super.getJavaFileForOutput(location, className, kind, sibling);
                }
            }
        };

        // 返回FileObject给compiler用，只一个
        StringBuilderJavaSource source = new StringBuilderJavaSource("myFirstClass");
        source.append("package x;");
        source.append("public class myFirstClass extends " + "BasicTypes" + " {");
        source.append("    public void f(){System.out.println(\"myFirstClass.f()\");}");
        source.append("}");
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector,
                null, null, Arrays.asList(source));
        Boolean result = task.call();
        if (!result) {
            System.out.println("Compilation failed");
            System.exit(1);
        }

        // 从编译好的结果中提取Class信息
        /*
        Map<String, byte[]> byteCodeMap = new HashMap<>(0);
        for (ByteArrayJavaClass cl : classFileObjects) {
            byteCodeMap.put(cl.getName().substring(1), cl.getBytes());
            ClassLoader loader = new MapClassLoader(byteCodeMap);
            try {
                BasicTypes bt = (BasicTypes) loader.loadClass("x.myFirstClass").newInstance();
                bt.f();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
    }
}