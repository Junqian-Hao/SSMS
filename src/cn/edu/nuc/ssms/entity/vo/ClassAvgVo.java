package cn.edu.nuc.ssms.entity.vo;

/**
 * @Author 王启良
 * @Date 2017/5/12 15:35
 * @Description : 班级平均成绩值对象
 */
public class ClassAvgVo {

    /**
     * 科目名称
     */
    private String subjectname;
    /**
     * 学期
     */
    private Integer term;
    /**
     * 平均成绩
     */
    private Double avgGrade;

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }
}
