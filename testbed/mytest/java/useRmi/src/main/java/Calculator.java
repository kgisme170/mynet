import java.rmi.Remote;
public interface Calculator extends Remote {
    long add(long a, long b) throws java.rmi.RemoteException;

    long sub(long a, long b) throws java.rmi.RemoteException;

    long mul(long a, long b) throws java.rmi.RemoteException;

    long div(long a, long b) throws java.rmi.RemoteException;
}