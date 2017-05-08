package cn.edu.nuc.ssms.service.impl;

import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.po.StudentExample;
import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.entity.po.UserExample;
import cn.edu.nuc.ssms.mapper.StudentMapper;
import cn.edu.nuc.ssms.mapper.UserMapper;
import cn.edu.nuc.ssms.service.LoginService;
import cn.edu.nuc.ssms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/25 8:38
 * @Description :用户登录操作
 */
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentMapper studentMapper;

    @Override
    public User login(User user) {
        int userid = user.getUserid();
        int idLength = (userid + "").length();
        if (idLength == 4) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andUseridEqualTo(user.getUserid()).andUserpasswordEqualTo(user.getUserpassword());
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size() != 0) {

                User loginUser = users.get(0);
                return loginUser;
            }
        } else if (idLength == 10) {
            StudentExample studentExample = new StudentExample();
            StudentExample.Criteria criteria = studentExample.createCriteria();
            criteria.andUseridEqualTo(user.getUserid()).andUserpasswordEqualTo(user.getUserpassword());
            List<Student> students = studentMapper.selectByExample(studentExample);
            if (students.size() != 0) {
            Student loginStudent = students.get(0);
                User loginUser = Utils.studentToUser(loginStudent);
                return loginUser;
            }
        }
        return null;
    }
}
