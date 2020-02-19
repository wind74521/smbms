package cn.wind.service.user;

import cn.wind.pojo.User;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-17
 */
public interface UserService {
    /**
     * 增加用户信息
     * @param user
     * @return
     */
    boolean addUser(User user);

    /**
     * 用户登录
     * @param userCode
     * @param userPassword
     * @return
     */
    User findLogin(String userCode,String userPassword);

    /**
     * 根据条件查询用户列表
     * @param userName
     * @param userRole
     * @return
     */
    List<User> findUserList(String userName, int userRole, int currentPageNo, int pageSize);
    /**
     * 根据条件查询用户表记录数
     * @param userName
     * @param userRole
     * @return
     */
    int findUserCount(String userName,int userRole);

    /**
     * 根据userCode查询出User
     * @param userCode
     * @return
     */
    User findUserCodeExist(String userCode);

    /**
     * 根据ID删除user
     * @param delId
     * @return
     */
    boolean deleteUserById(Integer delId);

    /**
     * 根据ID查找user
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 根据userId修改密码
     * @param id
     * @param pwd
     * @return
     */
    boolean updatePwd(int id,String pwd);
}
