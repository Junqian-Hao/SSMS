package cn.edu.nuc.ssms.test;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Notice;
import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.entity.vo.AnalyseVo;
import cn.edu.nuc.ssms.entity.vo.SupperVo;
import cn.edu.nuc.ssms.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/28 21:02
 * @Description : 学生角色业务逻辑单元测试
 */
public class StudentServiceTest {
    ApplicationContext applicationContext;
    @Autowired
    StudentService studentService;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:config/applicationContext-*.xml");
        studentService = (StudentService) applicationContext.getBean("studentService");
    }
    @Test
    public void testSelectAllNodice() {
        List<Notice> notices = studentService.selectAllNodice();
        System.out.println(notices);
    }

    @Test
    public void testSelectNodice() {
        List<Notice> notices = studentService.selectNodice(1, 2);
        System.out.println(notices);
    }
    @Test
    public void testSelectStudentCustomByClass() {
        StudentCustom studentCustom = new StudentCustom();
        studentCustom.setClassname("1234");
        List<StudentCustom> studentCustoms = studentService.selectStudentCustomByClass(studentCustom);
        System.out.println(studentCustoms);
    }

    @Test
    public void testSelectStudentByClass() {
        Student student = new Student();
        student.setClassname("1234");
        List<Student> students = studentService.selectStudentByClass(student);
        System.out.println(students);
    }

    @Test
    public void testAnalyseByClass() {
        AnalyseVo analyseVo = new AnalyseVo();
        analyseVo.setClassName("1234");

        List<Map<String,Object>> stringMapMap = studentService.analyseByClass(analyseVo);
        System.out.println(stringMapMap);
    }

    @Test
    public void testAnalyseByCollage() {
        AnalyseVo analyseVo = new AnalyseVo();
        analyseVo.setCollege("软件学院");
        List<Map<String,Object>> stringMapMap = studentService.analyseByCollage(analyseVo);
        System.out.println(stringMapMap);
    }
    @Test
    public void testSelectSupperVoById(){
        User user = new User();
        user.setUserid(1112);
        SupperVo supperVo = studentService.selectSupperVoById(user);
        System.out.println(supperVo);
    }
    @Test
    public void testSelectAllStudentId(){
        List<Integer> integers = studentService.selectAllStudentId();
        System.out.println(integers);
    }
}
