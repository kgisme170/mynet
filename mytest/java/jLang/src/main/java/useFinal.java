import java.util.HashSet;
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
    }
}
