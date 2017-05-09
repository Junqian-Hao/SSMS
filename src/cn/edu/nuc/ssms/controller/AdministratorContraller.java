package cn.edu.nuc.ssms.controller;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Grade;
import cn.edu.nuc.ssms.entity.po.Notice;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.service.AdministratorService;
import cn.edu.nuc.ssms.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/26 22:19
 * @Description :管理员特有功能
 */

@Controller
@RequestMapping("/Administrator")
public class AdministratorContraller {
    private static final Log log = LogFactory.getLog(AdministratorContraller.class);

    @Autowired
    AdministratorService administratorService;

    /**
     * 查询所有教师信息
     * @return
     */
    @RequestMapping("/selectAllTeacher")
    @ResponseBody
    public List<User> selectAllTeacher(){
        List<User> users = administratorService.selectAllTeacher();
        log.debug("查询所有教师信息");
        return users;
    }

    /**
     * 查询管理员信息
     * @param request
     * @return
     */
    @RequestMapping("/selectAdministrator")
    @ResponseBody
    public User selectAdministrator(HttpServletRequest request) {
        log.debug("查询管理员信息");
        Cookie[] cookies = request.getCookies();
        int userId = Utils.getUserIdCookie(cookies);
        User user = administratorService.selectAdministrator(userId);
        return user;
    }

    /**
     * 修改管理员信息
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/updateAdministrator")
    @ResponseBody
    public String updateAdministrator(@RequestBody User user,HttpServletRequest request) {
        log.debug("修改管理员信息");
        int userIdCookie = Utils.getUserIdCookie(request.getCookies());
        user.setUserid(userIdCookie);
        if (administratorService.updateAdministrator(user)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 添加教师用户
     * @param user
     * @return
     */
    @RequestMapping("/insertTeacher")
    @ResponseBody
    public String insertTeacher(@RequestBody User user) {
        log.debug("添加教师");
        User teacher = administratorService.insertTeacher(user);
        if (teacher != null) {
            Integer userid = teacher.getUserid();
            log.debug("添加教师成功");
            return "{\"code\": \"0\",\"userid\": \""+teacher.getUserid()+"\"}";
        }
        log.debug("添加教师失败");
        return "{\"code\": \"1\"}";
    }

    /**
     * 根据账号删除教师
     * @param user
     * @return
     */
    @RequestMapping("/deleteTeacher")
    @ResponseBody
    public String deleteTeacher(@RequestBody User user) {
        log.debug("删除教师");
        if (user.getUserid() != 0) {
            boolean b = administratorService.deleteTeacher(user);
            if (b) {
                log.debug("删除教师成功");
                return "{\"code\": \"0\"}";
            }
        }
        log.debug("删除教师失败");
        return "{\"code\": \"1\"}";
    }

    /**
     * 添加公告
     * @param notice
     * @return
     */
    @RequestMapping("/insertNotice")
    @ResponseBody
    public String insertNotice(@RequestBody Notice notice){
        log.debug("添加公告");
        if (notice != null) {
            notice.setAddtime(new Date());
            if (administratorService.insertNotice(notice)) {
                return "{\"code\": \"0\"}";
            }
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 删除公告
     * @param notice
     * @return
     */
    @RequestMapping("/deleteNotice")
    @ResponseBody
    public String deleteNotice(@RequestBody Notice notice) {
        log.debug("删除公告");
        if (administratorService.deleteNotice(notice.getNoticeid())) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 修改公告
     * @param notice
     * @return
     */
    @RequestMapping("/updateNotice")
    @ResponseBody
    public String updateNotice(@RequestBody Notice notice) {
        log.debug("修改公告");
        if (administratorService.updateNotice(notice)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 添加科目
     * @param subject
     * @return
     */
    @RequestMapping("/insertSubject")
    @ResponseBody
    public String insertSubject(@RequestBody Subject subject) {
        log.debug("添加科目");
        if (administratorService.insertSubject(subject)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 删除科目
     * @param subject
     * @return
     */
    @RequestMapping("/deleatSubject")
    @ResponseBody
    public String deleatSubject(@RequestBody Subject subject) {
        log.debug("删除科目");
        if (administratorService.deleatSubject(subject.getSubjectid())) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 修改科目
     * @param subject
     * @return
     */
    @RequestMapping("/updateSubject")
    @ResponseBody
    public String updateSubject(@RequestBody Subject subject) {
        log.debug("修改科目");
        if (administratorService.updateSubject(subject)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 查询所有科目
     * @return
     */
    @RequestMapping("/selectAllSubject")
    @ResponseBody
    public List<Subject> selectAllSubject() {
        log.debug("查询所有科目");
        List<Subject> subjects = administratorService.selectAllSubject();
        return subjects;
    }

    /**
     * 查询所有班级
     * @return
     */
    @RequestMapping("/selectAllClass")
    @ResponseBody
    public List<StudentCustom> selectAllClass() {
        log.debug("查询所有班级");
        List<StudentCustom> studentCustoms = administratorService.selectAllClass();
        return studentCustoms;
    }

    @RequestMapping("/insertGrads")
    @ResponseBody
    public String insertGrads(@RequestBody List<Grade> grades) {
        log.debug("添加所修课程");
        if (administratorService.insertGrade(grades)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 删除所修课程
     * @param grades
     * @return
     */
    @RequestMapping("/deleatGrads")
    @ResponseBody
    public String deleatGrads(@RequestBody List<Grade> grades) {
        log.debug("删除所修课程");
        if (administratorService.deleatGrade(grades)) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }
}
