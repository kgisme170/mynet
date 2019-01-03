package pkg;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)
interface ICalWebservice {
    public int add(int x, int y);
}
