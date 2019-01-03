package pkg;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;

@WebService(endpointInterface="pkg.IService")
//@SOAPBinding(style= SOAPBinding.Style.RPC)
public class ServiceImp implements IService{
    @Override
    public String hello(@WebParam(name = "username") String username) {
        System.out.println("hello " + username + " now is " + new Date());
        return "Hello " + username;
    }
}