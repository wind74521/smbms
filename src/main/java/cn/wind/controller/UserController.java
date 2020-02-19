package cn.wind.controller;

import cn.wind.config.Constants;
import cn.wind.config.JsonConfig;
import cn.wind.config.PageSupport;
import cn.wind.pojo.Role;
import cn.wind.pojo.User;
import cn.wind.service.role.RoleService;
import cn.wind.service.user.UserService;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 12:53
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value="/list")
    public String findUserList(Model model, String queryUserName, String queryUserRole, String pageIndex){

        Integer _queryUserRole = null;
        List<User> userList = null;
        List<Role> roleList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if(queryUserName == null){
            queryUserName = "";
        }
        if(queryUserRole != null && !queryUserRole.equals("")){
            _queryUserRole = Integer.parseInt(queryUserRole);
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/syserror";
            }
        }
        //总数量（表）
        int totalCount = 0;
        try {
            totalCount = userService.findUserCount(queryUserName,_queryUserRole);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        try {
            userList = userService.findUserList(queryUserName,_queryUserRole,currentPageNo,pageSize);

            roleList = roleService.findRoleList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.addAttribute("userList", userList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "user/userlist";

    }

    @GetMapping(value="/add")
    public String addUser(@ModelAttribute("user") User user){
        return "user/useradd";
    }

    //多文件上传
    @PostMapping(value="/addSave")
    public String addUserSave(User user, HttpSession session, HttpServletRequest request, MultipartFile[] multipartFiles){
        return "";

    }

    @GetMapping(value="/update/{id}")
    public String getUserById(@PathVariable String id, Model model, HttpServletRequest request){

        return "user/usermodify";
    }

    @PostMapping(value="/updateSave")
    public String updateUserSave(User user,HttpSession session,HttpServletRequest request, MultipartFile[] multipartFiles){

        return "user/usermodify";
    }

    @RequestMapping(value="/ucexist")
    @ResponseBody
    public Object userCodeIsExit(@RequestParam String userCode){
        logger.debug("userCodeIsExit userCode===================== "+userCode);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(userCode)){
            resultMap.put("userCode", "exist");
        }else{
            User user = null;
            try {
                user = userService.findUserCodeExist(userCode);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(null != user)
                resultMap.put("userCode", "exist");
            else
                resultMap.put("userCode", "noexist");
        }
        return JsonConfig.getJson(resultMap);
    }

    @GetMapping(value="/roleList")
    @ResponseBody
    public List<Role> getRoleList(){
        List<Role> roleList = null;
        try {
            roleList = roleService.findRoleList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug("roleList size: " + roleList.size());
        return roleList;
    }

    @GetMapping(value="/delUser")
    @ResponseBody
    public Object deluser(@RequestParam String id){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult", "notexist");
        }else{
            try {
                if(userService.deleteUserById(Integer.parseInt(id)))
                    resultMap.put("delResult", "true");
                else
                    resultMap.put("delResult", "false");
            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
        }
        return JsonConfig.getJson(resultMap);
    }

    @GetMapping(value="/view/{id}")
    public String view(@PathVariable String id,Model model,HttpServletRequest request){
        logger.debug("view id===================== "+id);
        User user = new User();

        model.addAttribute(user);
        return "user/userview";
    }

    @GetMapping(value="/pwdmodify")
    public String pwdModify(HttpSession session){
        if(session.getAttribute(Constants.USER_SESSION) == null){
            return "redirect:/user/login";
        }
        return "pwdmodify";
    }

    @PostMapping(value="/pwdModify")
    @ResponseBody
    public Object getPwdByUserId(@RequestParam String oldpassword,HttpSession session){
        logger.debug("getPwdByUserId oldpassword ===================== "+oldpassword);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(null == session.getAttribute(Constants.USER_SESSION) ){//session过期
            resultMap.put("result", "sessionerror");
        }else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
            resultMap.put("result", "error");
        }else{
            String sessionPwd = ((User)session.getAttribute(Constants.USER_SESSION)).getUserPassword();
            if(oldpassword.equals(sessionPwd)){
                resultMap.put("result", "true");
            }else{//旧密码输入不正确
                resultMap.put("result", "false");
            }
        }
        return JsonConfig.getJson(resultMap);
    }

    @RequestMapping(value="/pwdSave")
    public String pwdSave(@RequestParam(value="newpassword") String newPassword, HttpSession session, HttpServletRequest request){
        boolean flag = false;
        Object o = session.getAttribute(Constants.USER_SESSION);
        if(o != null && !StringUtils.isNullOrEmpty(newPassword)){
            try {
                flag = userService.updatePwd(((User)o).getId(),newPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(flag){
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                session.removeAttribute(Constants.USER_SESSION);//session注销
                return "login";
            }else{
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        }else{
            request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }
        return "pwdmodify";
    }
}
