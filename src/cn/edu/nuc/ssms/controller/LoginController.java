package cn.edu.nuc.ssms.controller;

import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.service.AdministratorService;
import cn.edu.nuc.ssms.service.LoginService;
import cn.edu.nuc.ssms.service.StudentService;
import cn.edu.nuc.ssms.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/25 8:11
 * @Description : 用户登录的控制器
 */
@Controller
public class LoginController {
    @Autowired
    LoginService loginService;
    @Autowired
    AdministratorService administratorService;
    @Autowired
    StudentService studentService;

    private static Logger log = Logger.getLogger(LoginController.class);


    /**
     * 执行登录请求
     * @param user     封装信息的user对象
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public
    @ResponseBody
    Map login(@RequestBody User user, HttpServletResponse response) {
        HashMap resultMassage = new HashMap();
        resultMassage.put("code", 1);
        log.debug("登录请求");
        if (user == null) {
            log.debug("密码错误");
            return resultMassage;
        }
        User login = loginService.login(user);
        if (login != null) {
            response.addCookie(new Cookie("userid", login.getUserid().toString()));
            log.debug("登录成功,用户名:" + login.getName() + "  类型:" + login.getType());
            resultMassage.put("code", 0);
            resultMassage.put("user", login);
            return resultMassage;
        }
        log.debug("登录失败");
        return resultMassage;
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/exit")
    @ResponseBody
    public String exit(HttpServletRequest request,HttpServletResponse response) {
        log.debug("退出登录");
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        for (Cookie cookie :cookies) {
            if ("userid".equals(cookie.getName())) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return "{\"code\": \"0\"}";
    }

    /**
     * 查询当前登录用户信息
     * @param request
     * @return
     */
    @RequestMapping("/selectNowUser")
    @ResponseBody
    public Student selectNowUser(HttpServletRequest request) {
        log.debug("查询当前登录用户信息");
        Cookie[] cookies = request.getCookies();
        int userIdCookie = Utils.getUserIdCookie(cookies);
        int idLength = (userIdCookie + "").length();
        if (idLength == 4) {
            User user = administratorService.selectAdministrator(userIdCookie);
            Student student = Utils.userToStudent(user);
            return student;
        }
        if (idLength == 10) {
            Student student = studentService.selectStudent(userIdCookie);
            return student;
        }
        return null;
    }
}
