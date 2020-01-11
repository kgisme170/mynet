import java.util.List;
/**
 * @author liming.gong
 */
public interface IUserDAO {
    /**
     * @param user
     */
    void addUser(User user);

    /**
     * @param id
     */
    void deleteUser(int id);

    /**
     * @param user
     */
    void updateUser(User user);

    /**
     * @param id
     * @return
     */
    String searchUserName(int id);

    /**
     * @param id
     * @return
     */
    User searchUser(int id);

    /**
     * @return
     */
    List<User> findAll();
}
