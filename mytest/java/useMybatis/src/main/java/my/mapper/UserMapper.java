package my.mapper;
import java.util.List;
import my.beans.UserBean;

public interface UserMapper {
    /**
     * 新增用戶
     * @param user
     * @return
     * @throws Exception
     */
    public int insertUser(UserBean user) throws Exception;
    public int updateUser (UserBean user,int id) throws Exception;
    public int deleteUser(int id) throws Exception;
    public UserBean selectUserById(int id) throws Exception;
    public List<UserBean> selectAllUser() throws Exception;
}