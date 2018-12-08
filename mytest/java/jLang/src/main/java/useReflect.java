import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
/**
 * @author liming.gong
 */
public class useReflect {
    public void test01(Map<String, String> m, List<String> l, String s) {

    }
    public static void main(String [] args){
        try {
            Method m2 = useReflect.class.getMethod("test01", Map.class, List.class, String.class);
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
