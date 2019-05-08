/**
 * @author liming.gong
 */

import javax.tools.*;
import java.net.URI;
import java.util.Arrays;

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

    public static void main(String [] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        JavaFileManager fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null);

        // 返回FileObject给compiler用，只一个
        StringBuilderJavaSource source = new StringBuilderJavaSource("MyFirstClass");
        source.append("package x;");
        source.append("public class MyFirstClass {");
        source.append("    public void f(){System.out.println(\"MyFirstClass.f()\");}");
        source.append("}");
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticCollector,
                null, null, Arrays.asList(source));
        Boolean result = task.call();
        if (!result) {
            System.out.println("Compilation failed");
            System.exit(1);
        } else {
            System.out.println("编译成功");
        }

    }
}