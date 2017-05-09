package cn.edu.nuc.ssms.controller;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Grade;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.entity.vo.SupperVo;
import cn.edu.nuc.ssms.service.TeacherService;
import cn.edu.nuc.ssms.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/29 0:29
 * @Description :教师角色的控制器
 */
@Controller
@RequestMapping("/Teacher")
public class TeacherController {
    private static final Log log = LogFactory.getLog(TeacherController.class);
    @Autowired
    TeacherService teacherService;

    /**
     * 查询全校所有学生信息
     *
     * @return
     */
    @RequestMapping("/selectAllStudent")
    @ResponseBody
    public List<StudentCustom> selectAllStudent() {
        log.debug("查询全校所有学生信息");
        List<StudentCustom> studentCustoms = teacherService.selectAllStudent();
        return studentCustoms;
    }

    /**
     * 查询当前教师用户
     *
     * @param request
     * @return
     */
    @RequestMapping("/selectTeacher")
    @ResponseBody
    public Map selectTeacher(HttpServletRequest request) {
        log.debug("查询当前教师用户");
        HashMap hashMap = new HashMap();
        hashMap.put("code", 1);
        Cookie[] cookies = request.getCookies();
        int userIdCookie = Utils.getUserIdCookie(cookies);
        if (userIdCookie != 0) {
            User teacher = teacherService.selectTeacher(userIdCookie);
            hashMap.put("code", 0);
            hashMap.put("teacher", teacher);
        }
        return hashMap;
    }

    /**
     * 教师修改个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/updateTeacher")
    @ResponseBody
    public String updateTeacher(@RequestBody User user) {
        log.debug("教师修改个人信息");
        if (user.getUserid() != 0) {
            teacherService.updateTeacher(user);
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 查询教师所教授的课程
     * @param user
     * @return
     */
    @RequestMapping("/selectTeachingClass")
    @ResponseBody
    public List<Subject> selectTeachingClass(@RequestBody User user) {
        log.debug("查询教师所教授的课程");
        List<Subject> subjects = teacherService.selectTeachingClass(user.getUserid());
        return subjects;
    }

    /**
     * 录入成绩
     * @param list
     * @return
     */
    @RequestMapping("/inputGrade")
    @ResponseBody
    public String inputGrade(@RequestBody List<Grade> list) {
        log.debug("录入成绩");
        for (Grade grade :
                list) {
            log.debug("科目id:" + grade.getSubjectid() + "->学生id:" + grade.getUserid()
                    + "->成绩:" + grade.getGrade());
        }
        boolean b = teacherService.inputGrade(list);
        if (b) {
            return "{\"code\": \"0\"}";
        }
        return "{\"code\": \"1\"}";
    }

    /**
     * 根据id查询教师或者学生的信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/selectSupperVoById")
    @ResponseBody
    public SupperVo selectSupperVoById(@RequestBody User user) {
        log.debug("根据id查询教师或者学生的信息");
        SupperVo supperVo = teacherService.selectSupperVoById(user);
        log.debug(supperVo);
        return supperVo;
    }


    /**
     * 通过excel表导入学生成绩
     * @return
     */
    @RequestMapping("/inputGradeByEXCEL")
    @ResponseBody
    public Map inputGradeByEXCEL(MultipartFile gradexcel, HttpServletRequest request) {
        log.debug("通过excel表插入成绩");
        HashMap resultMap = new HashMap<>();
        File file = null;
        resultMap.put("code", "1");
        try {
            if (gradexcel != null && !Utils.isEmpty(gradexcel.getOriginalFilename())) {
                String excel_path = "D:\\excel\\";

                file = new File(excel_path+"test.xls");
                gradexcel.transferTo(file);

                int userIdCookie = Utils.getUserIdCookie(request.getCookies());

                List<String> strings = teacherService.inputGradeByEXCEL(file, userIdCookie);

                if (strings != null) {
                    log.debug("插入失败");
                    resultMap.put("message", strings);
                    return resultMap;
                }
                resultMap.put("code", "0");
                log.debug("插入成功");
                return resultMap;
            }
        } catch (IOException e) {
            log.debug("插入失败");
            e.printStackTrace();
            return resultMap;
        }finally {
            file.delete();
            log.info("删除文件");
        }
        log.debug("插入失败");
        return resultMap;
    }
}
