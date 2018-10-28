package my;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    public void save() {
        System.out.println("保存用户");
    }
}
