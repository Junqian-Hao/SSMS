package cn.edu.nuc.ssms.entity.vo;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.po.Subject;
import cn.edu.nuc.ssms.entity.po.User;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/5/3 15:48
 * @Description :超级值对象
 */
public class SupperVo {
    User user;
    StudentCustom studentCustom;
    List<Subject> subjects;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StudentCustom getStudentCustom() {
        return studentCustom;
    }

    public void setStudentCustom(StudentCustom studentCustom) {
        this.studentCustom = studentCustom;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "SupperVo{" +
                "user=" + user +
                ", studentCustom=" + studentCustom +
                ", subjects=" + subjects +
                '}';
    }
}
