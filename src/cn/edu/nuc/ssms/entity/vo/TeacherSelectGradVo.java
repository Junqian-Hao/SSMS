package cn.edu.nuc.ssms.entity.vo;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/5/11 17:22
 * @Description :教师查询所授课程状况的vo
 */
public class TeacherSelectGradVo {
    List<TermVo> termVos;

    public List<TermVo> getTermVos() {
        return termVos;
    }

    public void setTermVos(List<TermVo> termVos) {
        this.termVos = termVos;
    }
}
