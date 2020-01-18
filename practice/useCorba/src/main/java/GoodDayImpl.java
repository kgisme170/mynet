import hello.*;
public class GoodDayImpl extends GoodDayPOA {
 
    @Override
    public String hello_simple() {
        // TODO Auto-generated method stub
        return "Hello from CORBA server";
    }
 
    @Override
    public String hello_wide(String msg) {
        // TODO Auto-generated method stub
        return "Hello from CORBA server, and the parameter 'msg' is: " + msg;
    }
}
