package cn.wind.controller;

import cn.wind.config.Constants;
import cn.wind.pojo.User;
import cn.wind.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Wind
 * @see 2020-02-19 15:07
 */
@Controller
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        logger.debug("LoginController welcome SMBMS==================");
        return "login";
    }

    @PostMapping(value="/dologin")
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword,
                          HttpServletRequest request, HttpSession session){

        //调用service方法，进行用户匹配
        User user = userService.findLogin(userCode,userPassword);
        if(null != user){//登录成功
            //放入session
            session.setAttribute(Constants.USER_SESSION, user);
            //页面跳转（frame.jsp）
            return "redirect:/main";
        }else{
            //页面跳转（login.jsp）带出提示信息--转发
            request.setAttribute("error", "用户名或密码不正确");
            return "login";
        }
    }

    @RequestMapping(value="/logout")
    public String logout(HttpSession session){
        //清除session
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }
    @RequestMapping(value="/main")
    public String main(){
        return "frame";
    }

    @RequestMapping(value="/syserror")
    public String sysError(){
        return "syserror";
    }
}
