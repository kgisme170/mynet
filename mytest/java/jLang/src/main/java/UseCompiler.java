import javax.annotation.processing.Processor;
import javax.tools.*;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

public class UseCompiler {
    public static void main(String args[]) {
        String fn = System.getProperty("user.dir") + "/java/jLang/src/main/java/useReflect.java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        try {
            OutputStream outStream = new FileOutputStream("core3.class");
            OutputStream errStream = new BufferedOutputStream(System.err);
            int result = compiler.run(null, outStream, errStream, "-sourcepath", "src", fn);
            System.out.println(result);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        String[] as = new String[]{fn};
        List<String> list = Arrays.asList(as);
        Iterable fileObjects = fileManager.getJavaFileObjectsFromStrings(list);
        for (Iterator it = fileObjects.iterator(); it.hasNext(); it.next()) {
            System.out.println("for loop");
        }
        Callable<Boolean> task = new JavaCompiler.CompilationTask() {
            @Override
            public void setProcessors(Iterable<? extends Processor> processors) {

            }

            @Override
            public void setLocale(Locale locale) {

            }

            @Override
            public Boolean call() {
                return true;
            }
        };
        try {
            if (!task.call()) {
                System.out.println("call not ok");
            }
            System.out.println("call ok");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test
        JavaFileManager jfm = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, final String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
                return null;
            }
        };
    }
}