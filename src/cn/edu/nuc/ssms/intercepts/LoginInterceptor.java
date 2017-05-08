package cn.edu.nuc.ssms.intercepts;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 王启良
 * @Date 2017/4/25 8:02
 * @Description :拦截未登录用户访问
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static Logger logger = Logger.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.debug("登录拦截器执行");
        String url = request.getRequestURI();
        if (url.indexOf("login.action") >= 0 || url.indexOf("checkCaptcha.action") >= 0
                || url.indexOf("getCaptchaImage.action") >= 0) {
            logger.debug("访问登录页面,拦截器放行");
            return true;
        }
        String userid = null;

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if ("userid".equals(cookie.getName())) {
                userid = cookie.getValue();
            }

        }
        if (userid != null) {
            //身份存在，放行
            logger.debug("身份存在，放行");
            return true;
        }
        logger.debug("身份不存在，跳转登录页面");
        request.getRequestDispatcher("login.html").forward(request, response);

        return false;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
