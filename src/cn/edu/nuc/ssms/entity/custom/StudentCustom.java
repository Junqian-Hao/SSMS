package cn.edu.nuc.ssms.entity.custom;

import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.vo.GradeVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/27 21:37
 * @Description :
 */
public class StudentCustom extends Student implements Serializable {
    /**
     * 成绩
     */
    List<GradeVo> gradeVos;

    public List<GradeVo> getGradeVos() {
        return gradeVos;
    }

    public void setGradeVos(List<GradeVo> gradeVos) {
        this.gradeVos = gradeVos;
    }
}
