package cn.edu.nuc.ssms.test;

import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.entity.po.UserExample;
import cn.edu.nuc.ssms.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by 16359 on 2017/4/22.
 * MyBatis测试
 */
public class App {
    ApplicationContext applicationContext;
     UserMapper userMapper;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:config/applicationContext-*.xml");
        userMapper = (UserMapper) applicationContext.getBean("userMapper");
    }
    @Test
    public void testLogin() {

        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUseridEqualTo(1111).andUserpasswordEqualTo("1");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users);
    }

}
