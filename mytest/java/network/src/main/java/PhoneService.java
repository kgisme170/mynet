import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

class Phone {
    private String name;
    private String owner;//拥有者
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
}

@WebService(serviceName="PhoneManager", targetNamespace="http://dd.ws.it.cn")
public class PhoneService {
    @WebMethod(operationName = "getMObileInfo")
    public @WebResult(name = "phone")
    Phone getPhoneInfo(@WebParam(name = "osName") String osName) {
        Phone phone = new Phone();
        if (osName.endsWith("android")) {
            phone.setName("android");
            phone.setOwner("google");
        } else if (osName.endsWith("ios")) {
            phone.setName("ios");
            phone.setOwner("apple");
        } else {
            phone.setName("windows phone");
            phone.setOwner("microsoft");
        }
        return phone;
    }

    @WebMethod(exclude = true)//把该方法排除在外
    public void sayHello(String city) {
        System.out.println("你好：" + city);
    }

    private void sayLuck(String city) {
        System.out.println("好友：" + city);
    }

    void sayGoodBye(String city) {
        System.out.println("拜拜:" + city);
    }

    protected void saySayalala(String city) {
        System.out.println("再见！" + city);
    }

    public static void main(String[] args) {
        String address1 = "http://127.0.0.1:8888/ws/phoneService";
        Endpoint.publish(address1, new PhoneService());
        System.out.println("wsdl地址 :" + address1 + "?WSDL");
    }
}