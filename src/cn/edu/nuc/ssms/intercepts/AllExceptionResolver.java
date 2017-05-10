package cn.edu.nuc.ssms.intercepts;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 王启良
 * @Date 2017/5/10 16:28
 * @Description : 全局异常处理
 */
public class AllExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        try {
            httpServletResponse.sendRedirect("error.html");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
