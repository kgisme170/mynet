import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class useSet{
    private final Set<String> store = new HashSet<String>();
    public useSet(){
        store.add("abc");
        store.add("xyz");
    }
    public void insert(String s){
        store.add(s);
    }
}
public class useFinal {
    public static void main(String [] args){
        useSet us = new useSet();
        us.insert("kk");

        try {
            Method m2 = useFinal.class.getMethod("test01", Map.class, List.class, String.class);
            Type[] t = m2.getGenericParameterTypes();
            System.out.println(t.length);
            for (Type paramType : t) {
                System.out.println("#" + paramType);
                if (paramType instanceof ParameterizedType) {
                    //获取泛型中的具体信息
                    Type[] genericTypes = ((ParameterizedType) paramType).getActualTypeArguments();
                    for (Type genericType : genericTypes) {
                        System.out.println("泛型类型参数：" + genericType);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
