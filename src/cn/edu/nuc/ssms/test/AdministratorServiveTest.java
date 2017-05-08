package cn.edu.nuc.ssms.test;

import cn.edu.nuc.ssms.entity.po.Notice;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.service.AdministratorService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * Created by 16359 on 2017/4/22.
 * MyBatis测试
 */
public class AdministratorServiveTest {
    ApplicationContext applicationContext;
     @Autowired
     AdministratorService administratorService;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:config/applicationContext-*.xml");
        administratorService = (AdministratorService) applicationContext.getBean("administratorService");
    }
    @Test
    public void testLogin() {
        List<User> users = administratorService.selectAllTeacher();
        System.out.println(users);
    }

    @Test
    public void testSelectAdministrator() {
        User user = administratorService.selectAdministrator(1111);
        System.out.println(user);
    }

    @Test
    public void testUpdateAdministrator() {
        User testUser = new User();
        testUser.setUserid(1111);
        testUser.setPhonenumber("00000000");
        boolean b = administratorService.updateAdministrator(testUser);
        System.out.println(b);
    }

    @Test
    public void testInsertTeacher() {
        User testUser = new User();
        testUser.setPhonenumber("99999");
        testUser.setName("单元测试插入教师");
        testUser.setSex("男");
        testUser.setUserpassword("1");
        User teacher = administratorService.insertTeacher(testUser);
        System.out.println(teacher.getUserid());
    }

    @Test
        public void testDeleteTeacher() {
        User testUser = new User();
        testUser.setUserid(1117);
        boolean b = administratorService.deleteTeacher(testUser);
        System.out.println(b);
    }

    @Test
    public void testInsertNotice() {
        Notice notice = new Notice();
        notice.setAddtime(new Date());
        notice.setMessage("我是测试信息");
        notice.setTitle("我是测试标题");
        boolean b = administratorService.insertNotice(notice);
        System.out.println(b);
    }

    @Test
    public void testDeleteNotice() {
        boolean b = administratorService.deleteNotice(1);
        System.out.println(b);
    }

    @Test
    public void testUpdateNotice() {
        Notice notice = new Notice();
        notice.setAddtime(new Date());
        notice.setMessage("我是测试修改信息");
        notice.setTitle("我是测试标题");
        notice.setNoticeid(2);
        boolean b = administratorService.updateNotice(notice);
        System.out.println(b);
    }
}
