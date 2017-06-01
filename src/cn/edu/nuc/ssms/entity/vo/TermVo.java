package cn.edu.nuc.ssms.entity.vo;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/5/11 18:57
 * @Description :
 */
public class TermVo {
     String subjectName;
    List<SubjectGradVo> subjectGradVos;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<SubjectGradVo> getSubjectGradVos() {
        return subjectGradVos;
    }

    public void setSubjectGradVos(List<SubjectGradVo> subjectGradVos) {
        this.subjectGradVos = subjectGradVos;
    }
}
