package cn.wind.controller;

import cn.wind.config.Constants;
import cn.wind.config.JsonConfig;
import cn.wind.pojo.Role;
import cn.wind.pojo.User;
import cn.wind.service.role.RoleService;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 16:54
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    private Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;


    @RequestMapping(value="/list")
    public String getRoleList(Model model){
        List<Role> roleList = null;
        try {
            roleList = roleService.findRoleList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("roleList", roleList);
        return "role/rolelist";
    }

    @GetMapping(value="/add")
    public String addRole(@ModelAttribute("role") Role role){
        return "roleadd";
    }

    @PostMapping(value="/addSave")
    public String addRoleSave(Role role, HttpSession session){
        role.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        role.setCreationDate(new Date());
        try {
            if(roleService.addRole(role)){
                return "redirect:/role/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "role/roleadd";
    }

    @GetMapping(value="/update/{id}")
    public String getUserById(@PathVariable String id, Model model, HttpServletRequest request){
        Role role = new Role();
        try {
            role = roleService.findRoleById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        model.addAttribute(role);
        return "role/rolemodify";
    }

    @PostMapping(value="/updateSave")
    public String modifyUserSave(Role role, HttpSession session){
        role.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        role.setModifyDate(new Date());
        try {
            if(roleService.updateRole(role)){
                return "redirect:/role/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "role/rolemodify";
    }
    @GetMapping(value="/delRole")
    @ResponseBody
    public Object deluser(@RequestParam String id){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult", "notexist");
        }else{
            try {
                if(roleService.deleteRoleById(Integer.parseInt(id)))
                    resultMap.put("delResult", "true");
                else
                    resultMap.put("delResult", "false");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return JsonConfig.getJson(resultMap);
    }

    @RequestMapping(value="/rcexist")
    @ResponseBody
    public Object roleCodeIsExit(@RequestParam String roleCode){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(roleCode)){
            resultMap.put("roleCode", "exist");
        }else{
            int count = 0;
            try {
                count = roleService.roleCodeIsExist(roleCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(count == 0)
                resultMap.put("roleCode", "noexist");
            else
                resultMap.put("roleCode", "exist");
        }
        return JsonConfig.getJson(resultMap);
    }

}
