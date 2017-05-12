package cn.edu.nuc.ssms.service.impl;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.*;
import cn.edu.nuc.ssms.entity.vo.AnalyseVo;
import cn.edu.nuc.ssms.entity.vo.GradeVo;
import cn.edu.nuc.ssms.entity.vo.SupperVo;
import cn.edu.nuc.ssms.mapper.NoticeMapper;
import cn.edu.nuc.ssms.mapper.StudentCustomMapper;
import cn.edu.nuc.ssms.mapper.StudentMapper;
import cn.edu.nuc.ssms.mapper.SubjectMapper;
import cn.edu.nuc.ssms.service.StudentService;
import cn.edu.nuc.ssms.service.TeacherService;
import cn.edu.nuc.ssms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @Author 王启良
 * @Date 2017/4/28 20:12
 * @Description :学生角色业务逻辑实现
 */
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    StudentCustomMapper studentCustomMapper;
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    TeacherService teacherService;
    @Override
    public List<Notice> selectAllNodice() {
        NoticeExample noticeExample = new NoticeExample();
        List<Notice> notices = noticeMapper.selectByExample(noticeExample);
        return notices;
    }

    @Override
    public List<Notice> selectNodice(int startRow, int pageSize) {
        NoticeExample noticeExample = new NoticeExample();
        noticeExample.setStartRow((startRow-1)*pageSize);
        noticeExample.setPageSize(pageSize);
        List<Notice> notices = noticeMapper.selectByExample(noticeExample);
        return notices;
    }

    @Override
    public Student selectStudent(int userID) {
        Student student = studentMapper.selectByPrimaryKey(userID);
        return student;
    }

    @Override
    public boolean updateStuden(Student student) {
        Student newStudent = studentMapper.selectByPrimaryKey(student.getUserid());
        if (Utils.isEmpty(student.getSex())) {
            student.setSex(newStudent.getSex());
        }
        if (Utils.isEmpty(student.getUserpassword())) {
            student.setUserpassword(newStudent.getUserpassword());
        }
        if (Utils.isEmpty(student.getPhonenumber())) {
            student.setPhonenumber(newStudent.getPhonenumber());
        }
        if (Utils.isEmpty(student.getName())) {
            student.setName(newStudent.getName());
        }
        if (Utils.isEmpty(student.getCollege())) {
            student.setCollege(newStudent.getCollege());
        }
        if (Utils.isEmpty(student.getClassname())) {
            student.setClassname(newStudent.getClassname());
        }
        if (Utils.isEmpty(student.getNativeplace())) {
            student.setNativeplace(newStudent.getNativeplace());
        }
        if (student.getBirthday() == null) {
            student.setBirthday(newStudent.getBirthday());
        }
        if (student.getUserid() != 0) {
            int i = studentMapper.updateByPrimaryKeySelective(student);
            if (i > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<StudentCustom> selectStudentCustomByClass(StudentCustom studentCustom) {
        List<StudentCustom> studentCustoms = studentCustomMapper.selectStudentCustomByClass(studentCustom);
        return studentCustoms;
    }

    @Override
    public List<Student> selectStudentByClass(Student student) {
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andClassnameEqualTo(student.getClassname());
        List<Student> students = studentMapper.selectByExample(studentExample);
        return students;
    }

    @Override
    public List<Subject> selectSubjectByTeacher(Subject subject) {
        SubjectExample subjectExample = new SubjectExample();
        SubjectExample.Criteria criteria = subjectExample.createCriteria();
        criteria.andUseridEqualTo(subject.getUserid());
        List<Subject> subjects = subjectMapper.selectByExample(subjectExample);
        return subjects;
    }

    @Override
    public List<Map<String,Object>> analyseByClass(AnalyseVo analyseVo) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        StudentCustom studentCustom = new StudentCustom();
        studentCustom.setClassname(analyseVo.getClassName());
        List<StudentCustom> studentCustoms = studentCustomMapper.selectStudentCustomByClass(studentCustom);
        List<GradeVo> gradeVos = studentCustoms.get(0).getGradeVos();

        for (GradeVo gradeVo : gradeVos) {
            String subject = gradeVo.getSubject();
            analyseVo.setSubjectName(subject);

            int passAmount = 0;
            int dispassAmount = 0;
            int goodAmount = 0;
            int perfectAmount = 0;

            float passPerentge = 0;
            float dispassPerentge = 0;
            float goodPerentge = 0;
            float perfectPerentge = 0;

            List<Float> floats = studentCustomMapper.selectGradBySubjectNameandClassName(analyseVo);
            int person = floats.size();
            for (float grad : floats) {
                if (grad > 90) {
                    perfectAmount++;
                }
                else if (grad > 80) {
                    goodAmount++;
                } else if (grad > 60) {
                    passAmount++;
                } else {
                    dispassAmount++;
                }
            }

            //计算比率

            perfectPerentge = (float) perfectAmount/person;
            goodPerentge = (float) goodAmount / person;
            passPerentge = (float) passAmount / person;
            dispassPerentge = (float) dispassAmount / person;

            HashMap<String, Object> subjectGrad = new HashMap<>();
            subjectGrad.put("perfectAmount", (float) perfectAmount);
            subjectGrad.put("perfectPerentge",perfectPerentge);
            subjectGrad.put("goodAmount", (float) goodAmount);
            subjectGrad.put("goodPerentge",goodPerentge);
            subjectGrad.put("passAmount", (float) passAmount);
            subjectGrad.put("passPerentge",passPerentge);
            subjectGrad.put("dispassAmount", (float) dispassAmount);
            subjectGrad.put("dispassPerentge",dispassPerentge);
            subjectGrad.put("subject", subject);

            resultList.add( subjectGrad);

        }
        return resultList;
    }

    @Override
    public List<Map<String,Object>> analyseByCollage(AnalyseVo analyseVo) {

        List<Map<String, Object>> resultList = new ArrayList<>();

        List<String> subjects = studentCustomMapper.selectSubjectNameByCollage(analyseVo);


        for (String subject : subjects) {
            analyseVo.setSubjectName(subject);

            int passAmount = 0;
            int dispassAmount = 0;
            int goodAmount = 0;
            int perfectAmount = 0;

                float passPerentge = 0;
            float dispassPerentge = 0;
            float goodPerentge = 0;
            float perfectPerentge = 0;

            List<Float> floats = studentCustomMapper.selectGradBySubjectNameandCollage(analyseVo);
            int person = floats.size();
            for (float grad : floats) {
                if (grad > 90) {
                    perfectAmount++;
                }
                else if (grad > 80) {
                    goodAmount++;
                } else if (grad > 60) {
                    passAmount++;
                } else {
                    dispassAmount++;
                }
            }

            //计算比率

            perfectPerentge = (float) perfectAmount/person;
            goodPerentge = (float) goodAmount / person;
            passPerentge = (float) passAmount / person;
            dispassPerentge = (float) dispassAmount / person;

            HashMap<String, Object> subjectGrad = new HashMap<>();
            subjectGrad.put("perfectAmount", (float) perfectAmount);
            subjectGrad.put("perfectPerentge",perfectPerentge);
            subjectGrad.put("goodAmount", (float) goodAmount);
            subjectGrad.put("goodPerentge",goodPerentge);
            subjectGrad.put("passAmount", (float) passAmount);
            subjectGrad.put("passPerentge",passPerentge);
            subjectGrad.put("dispassAmount", (float) dispassAmount);
            subjectGrad.put("dispassPerentge",dispassPerentge);

            subjectGrad.put("subject", subject);

            resultList.add(subjectGrad);

        }

        return resultList;
    }

    @Override
    public SupperVo selectSupperVoById(User user) {
        SupperVo supperVo = new SupperVo();
        int id = user.getUserid();
        int idLength = (id + "").length();
        if (idLength == 4) {
            User teacher = teacherService.selectTeacher(id);
            supperVo.setUser(teacher);

            List<Subject> subjects = teacherService.selectTeachingClass(id);
            supperVo.setSubjects(subjects);
        }
        if (idLength == 10) {
            List<StudentCustom> studentCustoms = studentCustomMapper.selectStudentCustomByStudenId(id);
            supperVo.setStudentCustom(studentCustoms.get(0));
        }
        return supperVo;
    }

    @Override
    public List<Integer> selectAllStudentId() {
        ArrayList<Integer> integers = new ArrayList<>();
        StudentExample studentExample = new StudentExample();
        List<Student> students = studentMapper.selectByExample(studentExample);
        for (Student student : students) {
            integers.add(student.getUserid());
        }
        return integers;
    }

    @Override
    public Set<String> selectAllClassName() {
        HashSet<String> strings = new HashSet<>();
        StudentExample studentExample = new StudentExample();
        List<Student> students = studentMapper.selectByExample(studentExample);
        for (Student student : students) {
            strings.add(student.getClassname());
        }
        return strings;
    }

    @Override
    public Set<String> selectAllColage() {
        HashSet<String> strings = new HashSet<>();
        StudentExample studentExample = new StudentExample();
        List<Student> students = studentMapper.selectByExample(studentExample);
        for (Student student : students) {
            strings.add(student.getCollege());
        }
        return strings;
    }
}
