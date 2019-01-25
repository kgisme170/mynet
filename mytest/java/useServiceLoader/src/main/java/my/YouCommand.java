package my;

public class YouCommand implements Command {
    @Override
    public void run() {
        System.out.println("YouCommand");
    }
}