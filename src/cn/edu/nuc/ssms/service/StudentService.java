package cn.edu.nuc.ssms.service;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Notice;
import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.vo.AnalyseVo;

import java.util.List;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/27 21:49
 * @Description :学生角色所具有的功能
 */
public interface StudentService {
    /**
     * 参看所有公告
     *
     * @return
     */
    List<Notice> selectAllNodice();

    /**
     * 分页查询公告
     *
     * @param startRow 起始索引
     * @param pageSize 页面容量
     * @return
     */
    List<Notice> selectNodice(int startRow, int pageSize);

    /**
     * 查询当前用户信息
     *
     * @return
     */
    Student selectStudent(int userID);

    /**
     * 修改学生信息不包括成绩
     *
     * @param student
     * @return
     */
    boolean updateStuden(Student student);

    /**
     *查询全班成绩
     * @param studentCustom
     * @return
     */
    List<StudentCustom> selectStudentCustomByClass(StudentCustom studentCustom);

    /**
     * 按班级查询学生
     * @param student
     * @return
     */
    List<Student> selectStudentByClass(Student student);

    /**
     * 查询教师所教授科目
     * @param subject
     * @return
     */
    List<Subject> selectSubjectByTeacher(Subject subject);

    /**
     * 获取班级的分析数据
     * @return
     */
    Map<String, Map<String,Float>> analyseByClass(AnalyseVo analyseVo);

    /**
     * 获取学院的分析数据
     * @return
     */
    Map<String, Map<String,Float>> analyseByCollage(AnalyseVo analyseVo);


}
