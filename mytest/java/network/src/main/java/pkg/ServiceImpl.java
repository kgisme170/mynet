package pkg;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
/**
 * @author liming.gong
 */
@WebService(endpointInterface="pkg.IService")

public class ServiceImpl implements IService{
    @Override
    public String hello(@WebParam(name = "username") String username) {
        System.out.println("hello " + username + " now is " + new Date());
        return "Hello " + username;
    }
}