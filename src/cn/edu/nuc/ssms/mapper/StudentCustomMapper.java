package cn.edu.nuc.ssms.mapper;

import cn.edu.nuc.ssms.entity.custom.StudentCustom;
import cn.edu.nuc.ssms.entity.vo.AnalyseVo;

import java.util.List;

/**
 * @Author 王启良
 * @Date 2017/4/28 23:59
 * @Description :学生扩展类的dao操作
 */
public interface StudentCustomMapper {
    List<StudentCustom> selectAllStudent();

    List<StudentCustom> selectStudentCustomByClass(StudentCustom studentCustom);

    List<StudentCustom> selectStudentCustomByCollage(StudentCustom studentCustom);

    List<StudentCustom> selectAllClass();

    List<StudentCustom> selectStudentCustomByStudenId(int id);

    List<Float> selectGradBySubjectNameandClassName(AnalyseVo analyseVo);

    List<Float> selectGradBySubjectNameandCollage(AnalyseVo analyseVo);

    List<String> selectSubjectNameByCollage(AnalyseVo analyseVo);
}
