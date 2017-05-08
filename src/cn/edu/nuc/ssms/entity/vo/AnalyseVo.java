package cn.edu.nuc.ssms.entity.vo;

/**
 * @Author 王启良
 * @Date 2017/5/6 11:15
 * @Description :成绩分析的值对象
 */
public class AnalyseVo {
    /**
     * 班级名称
     */
    String className;

    /**
     * 学院名称
     */
    String college;

    /**
     * 科目名称
     */
    String subjectName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
