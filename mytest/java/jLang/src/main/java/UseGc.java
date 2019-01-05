/**
 * @author liming.gong
 */
public class UseGc {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        super.finalize();
    }

    public static void main(String[] args) {
        UseGc useGc = new UseGc();
        useGc = null;
        System.gc();
    }
}