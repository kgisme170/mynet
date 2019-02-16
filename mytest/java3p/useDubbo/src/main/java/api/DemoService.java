package api;

import java.util.List;
/**
 * @author liming.gong
 */
public interface DemoService {
    /**
     * 获得permission
     * @param id
     * @return
     */
    List<String> getPermissions(Long id);
}