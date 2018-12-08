package my;

import org.springframework.stereotype.Service;
/**
 * @author liming.glm
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public void save() {
        System.out.println("保存用户");
    }
}
