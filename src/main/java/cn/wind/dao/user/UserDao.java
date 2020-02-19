package cn.wind.dao.user;

import cn.wind.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-17
 */
@Repository
@Scope
public interface UserDao {

    /*
     * 通过userCode获取User
     */
    User findLogin(String userCode);

    /*
     * 通过条件查询-userList
     */
    List<User> findUserList(@Param("userName") String userName, @Param("userRole") int userRole, @Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);

    /*
     * 通过条件查询-用户表记录数
     */
    int findUserCount(@Param("userName") String userName, @Param("userRole") int userRole);

    /*
     * 通过userId获取user
     */
    User findUserById(Integer id);

    /*
     * 增加用户信息
     */
    int addUser(User user);

    /*
     * 通过userId删除user
     */
    int deleteUserById(@Param("id") Integer delId);

    /*
     * 修改用户信息
     */
    int updateUser(User user);

    /*
     * 修改当前用户密码
     */
    int updatePwd(@Param("id") int id,@Param("pwd") String pwd);

}
