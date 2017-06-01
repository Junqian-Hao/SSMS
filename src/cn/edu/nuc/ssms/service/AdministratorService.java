package cn.edu.nuc.ssms.service;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Grade;
import cn.edu.nuc.ssms.entity.po.Notice;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.po.User;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/26 22:23
 * @Description : 管理员特有的业务逻辑
 */
public interface AdministratorService {

    /**
     * 查询管理员信息
     * @param userid 管理员id
     * @return 对应id的管理员的信息
     */
    User selectAdministrator(int userid);

    /**
     * 修改管理员信息
     * @param user 要修改的管理员信息
     * @return 修改的结果
     */
    boolean updateAdministrator(User user);

    /**
     * 添加教师
     * @param user 被添加的教师信息
     * @return 携带有教师工号的user对象
     */
    User insertTeacher(User user);

    /**
     * 删除教师
     * @param user 被删除的教师
     * @return 删除结果
     */
    boolean deleteTeacher(User user);

    /**
     * 查询所有教师账户
     * @return 所有的教师账户信息
     */
    List<User> selectAllTeacher();

    /**
     * 发布公告
     * @param notice 要发布的公告
     * @return
     */
    boolean insertNotice(Notice notice);

    /**
     * 删除公告
     * @param notice 被删除的公告编号
     * @return 删除结果
     */
    boolean deleteNotice(Notice[] notice);

    /**
     * 修改公告
     * @param notice 要修改的公告
     * @return 修改的结果
     */
    boolean updateNotice(Notice notice);

    /**
     * 查询所有公告,包括不参加滚动的
     * @return
     */
    List<Notice> selectAllNotices();

    /**
     * 添加科目
     * @param subject
     * @return
     */
    boolean insertSubject(Subject subject);

    /**
     * 删除科目
     * @param id
     * @return
     */
    boolean deleatSubject(int id);

    /**
     * 修改科目
     * @param subject
     * @return
     */
    boolean updateSubject(Subject subject);

    /**
     * 查询所有科目
     * @return
     */
    List<Subject> selectAllSubject();

    /**
     * 查询所有班级
     * @return
     */
    List<StudentCustom> selectAllClass();

    /**
     * 添加课程
     * @param grades
     * @return
     */
    boolean insertGrade(List<Grade> grades);

    /**
     * 删除科目成绩
     * @param grades
     * @return
     */
    boolean deleatGrade(List<Grade> grades);

    /**
     * 关联班级与课程
     * @param className
     * @param subjectId
     * @return
     */
    boolean classnameRileSubjectId(String className, int subjectId);
}
