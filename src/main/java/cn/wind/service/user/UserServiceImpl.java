package cn.wind.service.user;

import cn.wind.dao.user.UserDao;
import cn.wind.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-17
 */
@Service("userService")
@Scope
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User findLogin(String userCode,String userPassword) {
        System.out.println("userService=======>"+userCode);
        System.out.println("userService=======>"+userPassword);
        User user = userDao.findLogin(userCode);

        //匹配密码
        if(null != user){
            if(!user.getUserPassword().equals(userPassword))
                user = null;
        }

        return user;
    }

    @Override
    public List<User> findUserList(String userName, int userRole, int currentPageNo, int pageSize) {
        currentPageNo = (currentPageNo-1) * pageSize;
        return userDao.findUserList(userName,userRole,currentPageNo,pageSize);
    }

    @Override
    public int findUserCount(String userName, int userRole) {
        return userDao.findUserCount(userName,userRole);
    }

    @Override
    public User findUserCodeExist(String userCode) {
        return userDao.findLogin(userCode);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user) != 0;
    }

    @Override
    public boolean deleteUserById(Integer delId) {
        return userDao.deleteUserById(delId) != 0;
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user) != 0;
    }

    @Override
    public boolean updatePwd(int id, String pwd) {
        return userDao.updatePwd(id,pwd) != 0;
    }
}

