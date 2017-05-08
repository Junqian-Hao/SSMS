package cn.edu.nuc.ssms.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @Author 王启良
 * @Date 2017/4/25 19:44
 * @Description :生成,校验验证码
 */
@Controller
public class CaptchaController {
    private static final Log log = LogFactory.getLog(CaptchaController.class);
    @Autowired
    private Producer captchaProducer;

    /**
     * 页面入口
     * @return
     */
    @RequestMapping(value = "/index")
    public String getCaptcha(Model model,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        model.addAttribute("timestamp", System.currentTimeMillis());
//        request.setAttribute("timestamp",System.currentTimeMillis());
        request.getRequestDispatcher("login.html").forward(request,response);
        return null;
    }

    /**
     * 生成带验证码的图片
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
        @RequestMapping(value = "/getCaptchaImage")
    public ModelAndView getCaptchaImage(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();

        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        log.info("======生成了一个验证码，内容为：" + capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /**
     *
     * @param code
     * @param request
     * @return true或fasle,ture表示验证成功,false表示验证失败
     */
    @RequestMapping(value = "/checkCaptcha")
    @ResponseBody
    public String checkCaptcha(@RequestBody Map<String,String> code,
                               HttpServletRequest request) {
        String returnStr = "{\"code\": \"1\"}";
        String co = code.get("code");
        String original =(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        log.info("======用户输入的验证码：" + co);
        log.info("======正确的验证码：" + original);

        if (!StringUtils.isEmpty(code)) {
            if (co.equalsIgnoreCase(original)) {
                returnStr = "{\"code\": \"0\"}";
            }
        }

        log.info("======验证结果" + returnStr);
        return returnStr;

    }
}
