package my;
/**
 * @author liming.gong
 */
public class YouCommand implements Command {
    @Override
    public void run() {
        System.out.println("YouCommand");
    }
}