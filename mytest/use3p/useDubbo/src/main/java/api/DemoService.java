package api;

import java.util.List;
/**
 * @author liming.glm
 */
public interface DemoService {
    /**
     * 获得permission
     * @param id
     * @return
     */
    List<String> getPermissions(Long id);
}