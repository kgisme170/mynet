public class cpp2java {
    private static native void print();
    public static int intFunc(int i) {
        System.out.println("no exception");
        print();
        System.out.println("no exception");
        return i+1;
    }
    public static boolean boolFunc(boolean bool) {
        print();
        return !bool;
    }
    private static void mythrow() {
        System.out.println("test if private function call be called");
        youthrow();
    }
    private static void youthrow() {
        throw new ArrayIndexOutOfBoundsException();        
    }
    public static byte[] read(String s) {
        System.out.println(s);
        byte[] b = new byte[2];
        b[0]='a';
        b[1]='b';
        return b;
    }
    static {
        System.loadLibrary("impl");
    }

    public cpp2java() {
        System.out.println("ctor");
    }
    public int addone(int i){
        return i + 1;
    }
    private int intField;
    public cpp2java(int i) {
        intField = i;
        System.out.println("ctor(int)");
    }
}
