package cn.edu.nuc.ssms.entity.vo;

import java.io.Serializable;

/**
 * @Author 王启良
 * @Date 2017/4/28 23:53
 * @Description :成绩业务对象
 */
public class GradeVo implements Serializable {
    /**
     * 科目
     */
    String subject;

    /**
     * 科目id
     */
    int subjectid;
    /**
     * 成绩
     */
    Float grade;

    /**
     * 记录主键
     */
    int gradeid;

    public int getGradeid() {
        return gradeid;
    }

    public void setGradeid(int gradeid) {
        this.gradeid = gradeid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }
    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

}
