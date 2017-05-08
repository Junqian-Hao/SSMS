package cn.edu.nuc.ssms.service;

import cn.edu.nuc.ssms.entity.po.User;

/**
 * @Author 王启良
 * @Date 2017/4/25 8:13
 * @Description :用户登录操作
 */
public interface LoginService {
    /**
     * 教师管理员的登录操作
     * @param user 封装登录请求信息的user对象
     * @return
     */
    User login(User user);

}
