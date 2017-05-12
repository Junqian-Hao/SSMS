package cn.edu.nuc.ssms.entity.vo;

/**
 * @Author 王启良
 * @Date 2017/5/11 18:56
 * @Description :
 */
public class SubjectGradVo {
    /**
     * 及格率
     */
    double passPerentge;
    /**
     * 平均成绩
     */
    double avageGrad;

    public double getPassPerentge() {
        return passPerentge;
    }

    public void setPassPerentge(double passPerentge) {
        this.passPerentge = passPerentge;
    }

    public double getAvageGrad() {
        return avageGrad;
    }

    public void setAvageGrad(double avageGrad) {
        this.avageGrad = avageGrad;
    }
}
