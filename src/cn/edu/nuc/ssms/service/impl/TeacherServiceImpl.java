package cn.edu.nuc.ssms.service.impl;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.*;
import cn.edu.nuc.ssms.entity.vo.TeacherSelectGradVo;
import cn.edu.nuc.ssms.mapper.GradeMapper;
import cn.edu.nuc.ssms.mapper.StudentCustomMapper;
import cn.edu.nuc.ssms.mapper.SubjectMapper;
import cn.edu.nuc.ssms.mapper.UserMapper;
import cn.edu.nuc.ssms.service.TeacherService;
import cn.edu.nuc.ssms.util.ExcelUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/29 0:17
 * @Description :教师角色业务逻辑实现
 */
public class TeacherServiceImpl implements TeacherService {
    private static final Log log = LogFactory.getLog(TeacherServiceImpl.class);
    @Autowired
    StudentCustomMapper studentCustomMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    GradeMapper gradeMapper;


    @Override
    public User selectTeacher(int userid) {
        User user = userMapper.selectByPrimaryKey(userid);
        return user;
    }

    @Override
    public boolean updateTeacher(User user) {
        int code = userMapper.updateByPrimaryKeySelective(user);
        if (code > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<StudentCustom> selectAllStudent() {
        List<StudentCustom> studentCustoms = studentCustomMapper.selectAllStudent();
        return studentCustoms;
    }

    @Override
    public List<Subject> selectTeachingClass(int id) {
        SubjectExample subjectExample = new SubjectExample();
        SubjectExample.Criteria criteria = subjectExample.createCriteria();
        criteria.andUseridEqualTo(id);
        List<Subject> subjects = subjectMapper.selectByExample(subjectExample);
        return subjects;
    }

    @Override
    public boolean inputGrade(List<Grade> list) {
        try {
            for (Grade grade : list) {
                GradeExample gradeExample = new GradeExample();
                GradeExample.Criteria criteria = gradeExample.createCriteria();
                criteria.andUseridEqualTo(grade.getUserid());
                criteria.andSubjectidEqualTo(grade.getSubjectid());
                gradeMapper.updateByExampleSelective(grade, gradeExample);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }



    @Override
    public List<String> inputGradeByEXCEL(File file, int userid) {
        ArrayList<String> message = new ArrayList<>();
        try {
            InputStream is = new FileInputStream(file);
            ExcelUtil excelReader = new ExcelUtil();
            String[] titles = excelReader.readExcelTitle(is);

            log.debug("判断表头是否合法");
            if (!titles[0].equals("学号")) {
                log.debug("表第一列名称必须是学号");
                message.add("表第一列名称必须是学号");
                return message;
            }
            if (!titles[1].equals("姓名")) {
                log.debug("表第二列名称必须是姓名");
                message.add("表第二列名称必须是姓名");
                return message;
            }

            String subjectName = titles[2];
            log.debug("要录入的科目名称为:" + subjectName);
            log.debug("要录入成绩的教师id为:" + userid);

            SubjectExample subjectExample = new SubjectExample();
            SubjectExample.Criteria criteria = subjectExample.createCriteria();
            criteria.andUseridEqualTo(userid);
            criteria.andSubjectnameEqualTo(subjectName);
            List<Subject> subjects = subjectMapper.selectByExample(subjectExample);
            if (subjects == null || subjects.size() == 0) {
                log.debug("无法查询到科目id");
                message.add("无法查询到科目id,成绩表第三列表头必须为科目名称");
                return message;
            }

            Integer subjectid = subjects.get(0).getSubjectid();
            log.debug("科目id为:" + subjectid);

            InputStream is2 = new FileInputStream(file);
            Map<Integer, String> map = excelReader.readExcelContent(is2);

            for (int i = 1; i <= map.size(); i++) {
                Grade grade = new Grade();
                int studenId = 0;


                String s = map.get(i);
                log.debug("表的第" + i + "行内容为" + s);

                String[] split = s.split(" ");

                for (int p = 0; p < 3; p++) {
                    if (p == 0) {
                        studenId = new Integer(split[0]);
                        log.debug("学号为:" + studenId);
                    }
                    if (p == 2) {
                        Double subjectGrade = new Double(split[2]);
                        log.debug("成绩为:" + subjectGrade);
                        grade.setGrade(subjectGrade);
                    }

                }

                GradeExample gradeExample = new GradeExample();
                GradeExample.Criteria gradeCriteria = gradeExample.createCriteria();
                gradeCriteria.andUseridEqualTo(studenId);
                gradeCriteria.andSubjectidEqualTo(subjectid);
                List<Grade> grades = gradeMapper.selectByExample(gradeExample);
                if (grades.get(0).getGrade() != 0) {
                    log.debug("同学有成绩,禁止修改");
                    message.add("同学有成绩,禁止修改");
                    return message;
                }
                gradeMapper.updateByExampleSelective(grade, gradeExample);

                log.debug("一条记录插入成功");
            }

        } catch (Exception e) {
            message.add("您输入的信息有误");
            e.printStackTrace();
            return message;
        }
        return null;
    }

    @Override
    public List<TeacherSelectGradVo> selectTeacherGrade(User user) {
        List<TeacherSelectGradVo> teacherSelectGradVo = studentCustomMapper.selectTeacherGrade(user);
        return teacherSelectGradVo;
    }

    @Override
    public List<String> selectAllTeacherId() {

        ArrayList<String> integers = new ArrayList<>();
        UserExample userExample = new UserExample();
        List<User> users = userMapper.selectByExample(userExample);
        for (User user : users) {
            integers.add(String.valueOf(user.getUserid()));
        }
        return integers;
    }


}
