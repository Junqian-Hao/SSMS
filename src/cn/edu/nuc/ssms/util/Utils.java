package cn.edu.nuc.ssms.util;

import cn.edu.nuc.ssms.entity.po.Student;
import cn.edu.nuc.ssms.entity.po.User;

import javax.servlet.http.Cookie;

/**
 * @Author 王启良
 * @Date 2017/4/25 9:15
 * @Description :项目工具包
 */
public class Utils {
    /**
     * 将student对象转换成user对象
     * @param student 需要转换的user对象
     * @return 丢弃部分信息后的student对象
     */
    public static User studentToUser(Student student) {
        User user = new User();
        user.setUserid(student.getUserid());
        user.setUserpassword(student.getUserpassword());
        user.setSex(student.getSex());
        user.setName(student.getName());
        user.setPhonenumber(student.getPhonenumber());
        user.setType(student.getType());
        return user;
    }

    /**
     * 从cookie数组中取出useridcookie
     * @param cookies
     * @return
     */
    public static int getUserIdCookie(Cookie[] cookies) {
        int userId = 0;
        for (Cookie cookie :cookies) {
            if ("userid".equals(cookie.getName())) {
                userId = Integer.valueOf(cookie.getValue());
            }
        }
        return userId;
    }

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

}
