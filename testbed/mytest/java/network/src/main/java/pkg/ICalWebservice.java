package pkg;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)
interface ICalWebservice {
    /**
     * 加法
     * @param x
     * @param y
     * @return
     */
    int add(int x, int y);
}
