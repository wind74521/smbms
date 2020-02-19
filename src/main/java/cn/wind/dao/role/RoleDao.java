package cn.wind.dao.role;

import cn.wind.pojo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-18 21:16
 */
@Repository
@Scope
public interface RoleDao {

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
    int addRole(Role role);

    /**
     * 通过Id删除role
     * @param delId
     * @return
     */
    int deleteRoleById(@Param("id")Integer delId);

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    int updateRole(Role role);

    /**
     * 通过id获取role
     * @param id
     * @return
     */
    Role findRoleById(@Param("id")Integer id);

    /**
     * 根据roleCode ，进行角色编码的唯一性验证
     * @param roleCode
     * @return
     */
    int roleCodeIsExist(@Param("roleCode")String roleCode);

}
