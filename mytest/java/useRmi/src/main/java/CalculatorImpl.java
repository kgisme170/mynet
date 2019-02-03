import java.rmi.server.UnicastRemoteObject;
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    public CalculatorImpl() throws java.rmi.RemoteException {
        super();
    }
    @Override
    public long add(long a, long b) {
        return a + b;
    }
    @Override
    public long sub(long a, long b) {
        return a - b;
    }

    @Override
    public long mul(long a, long b) {
        return a * b;
    }

    @Override
    public long div(long a, long b) {
        return a / b;
    }
}