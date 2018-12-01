import javax.annotation.processing.Processor;
import javax.script.ScriptEngine;

import javax.script.*;
import javax.tools.JavaCompiler;
import com.sun.tools.javac.file.*;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;

import static java.lang.System.*;
public class useScript {
    static String fn = System.getProperty("user.dir") + "/java/jLang/src/main/java/useReflect.java";
    static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public static void main(String args[]) {
        f1();
        f2();
    }

    public static void f1() {
        ScriptEngineManager manager = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            out.printf("脚本引擎: %s%n" +
                            "Version: %s%n" +
                            "Language name: %s%n" +
                            "Language version: %s%n" +
                            "Extensions: %s%n" +
                            "Mime types: %s%n" +
                            "Names: %s%n",
                    factory.getEngineName(),
                    factory.getEngineVersion(),
                    factory.getLanguageName(),
                    factory.getLanguageVersion(),
                    factory.getExtensions(),
                    factory.getMimeTypes(),
                    factory.getNames());

            ScriptEngine engine = factory.getScriptEngine();
            try {
                engine.eval("n=9");
                Object result = engine.eval("1+n");
                System.out.println(result);

                Object n = engine.get("n");
                System.out.println(n);

                Bindings scope = engine.createBindings();
                scope.put("m", 2);
                System.out.println(engine.eval("m+1", scope));

                engine.eval("function greeter(how){this.how=how}");
                engine.eval("greeter.prototype.welcome = function(whom){return this.how +'.'+whom+'!'}");
                Object you = engine.eval(("new greeter('you')"));
                try {
                    result = ((Invocable) engine).invokeMethod(you, "welcome", "world");
                    System.out.println(result);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    Reader reader = new FileReader(System.getProperty("user.dir") + "/java/jLang/src/main/java/my.js");
                    CompiledScript script = null;
                    if (engine instanceof Compilable) {
                        script = ((Compilable) engine).compile(reader);
                    }
                    if (script != null) {
                        script.eval();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }

    public static void f2() {
        System.out.println("f2");
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
        System.out.println("2");
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
    }
}