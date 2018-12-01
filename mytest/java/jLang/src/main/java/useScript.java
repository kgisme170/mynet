import javax.script.ScriptEngine;

import javax.script.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.*;
import static java.lang.System.*;
public class useScript {
    public static void main(String args[]) {
        ScriptEngineManager manager = new ScriptEngineManager();
        // 得到所有的脚本引擎工厂

        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        // 这是Java SE 5 和Java SE 6的新For语句语法

        for (ScriptEngineFactory factory : factories) {
            // 打印脚本信息

            out.printf("Name: %s%n" +
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
            // 得到当前的脚本引擎

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
                try {//接口之间如何互转?
                    result = ((Invocable) engine).invokeMethod(you, "welcome", "world");
                    System.out.println(result);
                }catch(NoSuchMethodException e){
                    e.printStackTrace();
                }
                try {
                    Reader reader = new FileReader("my.js");
                    CompiledScript script = null;
                    if(engine instanceof Compilable){
                        script = ((Compilable)engine).compile(reader);
                    }
                    if(script!=null){
                        script.eval();
                    }
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                //engine.getContext().setWriter(new PrintWriter(new StringWriter(), true));
                //engine.eval("out.println(\"hello\")");
            }catch(ScriptException e){
                e.printStackTrace();
            }
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            try {
                OutputStream outStream = new FileOutputStream("core3.class");
                OutputStream errStream = new BufferedOutputStream(System.err);
                int result = compiler.run(null, outStream, errStream, "-sourcepath", "src", "core3.java");
            } catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}