package com.wle.blog.service;

import com.wle.blog.pojo.SysUser;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 退出登录
     * @param token
     * @return
     */

    Result logout(String token);

    /**
     * 登录
     * @param loginParams
     * @return
     */

    Result login(LoginParams loginParams);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);

    /**
     *检测token
     * @param token
     * @return
     */

    SysUser checkToken(String token);
}
