package cn.edu.nuc.ssms.service;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Grade;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.entity.vo.TeacherSelectGradVo;

import java.io.File;
import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/27 21:14
 * @Description : 教师用户业务逻辑
 */
public interface TeacherService {

    /**
     * 查询教师信息
     *
     * @param userid 教师id
     * @return 对应id的管理员的信息
     */
    User selectTeacher(int userid);

    /**
     * 修改教师信息
     *
     * @param user 要修改的教师信息
     * @return 修改的结果
     */
    boolean updateTeacher(User user);

    /**
     * 查询所有学生账户
     *
     * @return 所有的学生账户信息
     */
    List<StudentCustom> selectAllStudent();

    /**
     * 查询教师所教课程
     * @param id
     * @return
     */
    List<Subject> selectTeachingClass(int id);

    /**
     * 教师录入成绩
     * @param list
     * @return
     */
    boolean inputGrade(List<Grade> list);


    /**
     * 教师通过excle表插入成绩
     * @param file
     * @param userid
     * @return
     */
    List<String> inputGradeByEXCEL(File file,int userid);

    /**
     * 教师查询所授课程成绩
     * @param user
     * @return
     */
    List<TeacherSelectGradVo> selectTeacherGrade(User user);
    /**
     *模糊查询教师id
     * @return
     */
    List<Integer> selectAllTeacherId();
}
