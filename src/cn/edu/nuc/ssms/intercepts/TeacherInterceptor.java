package cn.edu.nuc.ssms.intercepts;

import cn.edu.nuc.ssms.entity.po.User;
import cn.edu.nuc.ssms.mapper.UserMapper;
import cn.edu.nuc.ssms.user.UesrType;
import cn.edu.nuc.ssms.util.Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 王启良
 * @Date 2017/5/9 8:38
 * @Description :拦截低权限访问教师接口
 */
public class TeacherInterceptor implements HandlerInterceptor {
    private static final Log log = LogFactory.getLog(TeacherInterceptor.class);

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.debug("教师权限拦截器");

        int userId = Utils.getUserIdCookie(httpServletRequest.getCookies());
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null && (user.getType() == UesrType.ADMIN || user.getType() == UesrType.TEACHER)) {
            log.debug("当前用户为管理员或教师权限,放行");
            return true;
        }

        log.debug("权限不足,跳转登录页面");
        httpServletRequest.getRequestDispatcher("../login2.html").forward(httpServletRequest, httpServletResponse);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
