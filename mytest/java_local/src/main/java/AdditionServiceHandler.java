import org.apache.thrift.TException;

public class AdditionServiceHandler implements AdditionService.Iface {
    @Override
    public int add(int n1, int n2) {
        return n1 + n2;
    }
}