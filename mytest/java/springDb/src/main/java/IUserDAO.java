import java.util.List;

public interface IUserDAO {

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    String searchUserName(int id);

    User searchUser(int id);

    List<User> findAll();
}