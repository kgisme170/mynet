import javax.script.ScriptEngine;

import javax.script.*;
import java.io.*;
import java.util.*;

import static java.lang.System.*;
public class useScript {
    public static void main(String args[]) {
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
}