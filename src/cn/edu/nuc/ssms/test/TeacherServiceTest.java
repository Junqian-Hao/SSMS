package cn.edu.nuc.ssms.test;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.entity.vo.SupperVo;
import cn.edu.nuc.ssms.service.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/29 0:23
 * @Description :教师角色业务逻辑单元测试
 */
public class TeacherServiceTest {
    ApplicationContext applicationContext;
    @Autowired
    TeacherService teacherService;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:config/applicationContext-*.xml");
        teacherService = (TeacherService) applicationContext.getBean("teacherService");
    }
    @Test
    public void testSelectAllStudent() {
        List<StudentCustom> studentCustoms = teacherService.selectAllStudent();
        System.out.println(studentCustoms);
    }
    @Test
    public void testSelectSupperVoById(){
        User user = new User();
        user.setUserid(1112);
        SupperVo supperVo = teacherService.selectSupperVoById(user);
        System.out.println(supperVo);
    };
}
