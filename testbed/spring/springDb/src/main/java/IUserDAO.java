import java.util.List;
/**
 * @author liming.gong
 */
public interface IUserDAO {
    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(int id);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);

    /**
     * 查找用户名
     * @param id
     * @return
     */
    String searchUserName(int id);

    /**
     * 搜索用户名
     * @param id
     * @return
     */
    User searchUser(int id);

    /**
     * 返回所有用户
     * @return
     */
    List<User> findAll();
}