package cn.wind.service.role;

import cn.wind.pojo.Role;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 10:56
 */
public interface RoleService {
    /**
     * 获取角色列表
     * @return
     */
    List<Role> findRoleList();
    /**
     * 增加角色信息
     * @param role
     * @return
     */
    boolean addRole(Role role);

    /**
     * 通过Id删除role
     * @param delId
     * @return
     */
    boolean deleteRoleById(Integer delId);

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    boolean updateRole(Role role);


    /**
     * 通过id获取role
     * @param id
     * @return
     */
    Role findRoleById(Integer id);

    /**
     * 根据roleCode ，进行角色编码的唯一性验证
     * @param roleCode
     * @return
     */
    int roleCodeIsExist(String roleCode);

}
