package cn.edu.nuc.ssms.entity.vo;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/5/11 18:57
 * @Description :
 */
public class TermVo {
    int termId;
    List<SubjectGradVo> subjectGradVos;

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public List<SubjectGradVo> getSubjectGradVos() {
        return subjectGradVos;
    }

    public void setSubjectGradVos(List<SubjectGradVo> subjectGradVos) {
        this.subjectGradVos = subjectGradVos;
    }
}
