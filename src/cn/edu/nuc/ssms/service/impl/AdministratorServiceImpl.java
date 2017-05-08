package cn.edu.nuc.ssms.service.impl;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.*;
import cn.edu.nuc.ssms.mapper.*;
import cn.edu.nuc.ssms.service.AdministratorService;
import cn.edu.nuc.ssms.user.UesrType;
import cn.edu.nuc.ssms.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/27 12:13
 * @Description :
 */
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    StudentCustomMapper studentCustomMapper;
    @Autowired
    GradeMapper gradeMapper;

    @Override
    public User selectAdministrator(int user) {
            User userMassage = userMapper.selectByPrimaryKey(user);
            return userMassage;

    }

    @Override
    public boolean updateAdministrator(User user) {
        User newUser = userMapper.selectByPrimaryKey(user.getUserid());
        if (Utils.isEmpty(user.getName())) {
            user.setName(newUser.getName());
        }
        if (Utils.isEmpty(user.getPhonenumber())) {
            user.setPhonenumber(newUser.getPhonenumber());
        }
        if (Utils.isEmpty(user.getUserpassword())) {
            user.setUserpassword(newUser.getUserpassword());
        }
        if (Utils.isEmpty(user.getSex())) {
            user.setSex(newUser.getSex());
        }
        int i = userMapper.updateByPrimaryKeySelective(user);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User insertTeacher(User user) {
        user.setType(UesrType.TEACHER);
        int i = userMapper.insertSelective(user);
        if (i > 0) {
            return user;
        }
        return null;
    }

    @Override
    public boolean deleteTeacher(User user) {
        int i = userMapper.deleteByPrimaryKey(user.getUserid());
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> selectAllTeacher() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTypeEqualTo(UesrType.TEACHER);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    @Override
    public boolean insertNotice(Notice notice) {
        if (notice != null) {
        int i = noticeMapper.insertSelective(notice);
            if (i > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteNotice(int notice) {
        int i = noticeMapper.deleteByPrimaryKey(notice);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNotice(Notice notice) {
        if (notice != null && notice.getNoticeid() != 0) {
        int i = noticeMapper.updateByPrimaryKeySelective(notice);
            if (i > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean insertSubject(Subject subject) {
        int insert = subjectMapper.insert(subject);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleatSubject(int id) {
        int i = subjectMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSubject(Subject subject) {
        int i = subjectMapper.updateByPrimaryKeySelective(subject);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Subject> selectAllSubject() {
        SubjectExample subjectExample = new SubjectExample();
        List<Subject> subjects = subjectMapper.selectByExample(subjectExample);
        return subjects;
    }

    @Override
    public List<StudentCustom> selectAllClass() {
        List<StudentCustom> studentCustoms = studentCustomMapper.selectAllClass();
        return studentCustoms;
    }

    @Override
    public boolean insertGrade(List<Grade> grades) {
        try {
            for (Grade grade :grades) {
                gradeMapper.insertSelective(grade);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleatGrade(List<Grade> grades) {
        try {
            for (Grade grade :grades) {
                gradeMapper.deleteByPrimaryKey(grade.getGradeid());
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
