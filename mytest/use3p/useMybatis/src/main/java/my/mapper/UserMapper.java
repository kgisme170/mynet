package my.mapper;
import java.util.List;
import my.beans.UserBean;
/**
 * @author liming.gong
 */
public interface UserMapper {
    /**
     * 新增用戶
     * @param user
     * @return
     * @throws Exception
     */
    int insertUser(UserBean user) throws Exception;

    /**
     * 更新用户
     * @param user
     * @param id
     * @return
     * @throws Exception
     */
    int updateUser (UserBean user,int id) throws Exception;

    /**
     * 删除用户
     * @param id
     * @return
     * @throws Exception
     */
    int deleteUser(int id) throws Exception;

    /**
     * 搜索用户
     * @param id
     * @return
     * @throws Exception
     */
    UserBean selectUserById(int id) throws Exception;

    /**
     * 选择所有用户
     * @return
     * @throws Exception
     */
    List<UserBean> selectAllUser() throws Exception;
}