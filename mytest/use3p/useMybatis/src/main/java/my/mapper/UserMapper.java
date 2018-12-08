package my.mapper;
import java.util.List;
import my.beans.UserBean;
/**
 * @author liming.glm
 */
public interface UserMapper {
    /**
     * 新增用戶
     * @param user
     * @return
     * @throws Exception
     */
    int insertUser(UserBean user) throws Exception;
    int updateUser (UserBean user,int id) throws Exception;
    int deleteUser(int id) throws Exception;
    UserBean selectUserById(int id) throws Exception;
    List<UserBean> selectAllUser() throws Exception;
}