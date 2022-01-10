package com.wle.blog.utils;

import com.wle.blog.pojo.SysUser;


/**
 * 用户信息放入本地
 */
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    private static ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();

    }

}
