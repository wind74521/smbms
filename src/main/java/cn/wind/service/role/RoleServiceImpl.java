package cn.wind.service.role;

import cn.wind.dao.role.RoleDao;
import cn.wind.dao.user.UserDao;
import cn.wind.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 10:56
 */
@Service("roleService")
@Scope
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Role> findRoleList() {
        return roleDao.findRoleList();
    }

    @Override
    public boolean addRole(Role role) {
        return roleDao.addRole(role) !=0 ;
    }

    @Override
    public boolean deleteRoleById(Integer delId) {
        boolean flag =false;
        if(userDao.findUserCount(null, delId) == 0){
            if(roleDao.deleteRoleById(delId) > 0)
                flag = true;
        }
        return flag;
    }

    @Override
    public boolean updateRole(Role role) {
        return roleDao.updateRole(role) != 0;
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public int roleCodeIsExist(String roleCode) {
        return roleDao.roleCodeIsExist(roleCode);
    }
}
