package com.wle.blog.service;



import com.wle.blog.pojo.SysUser;
import com.wle.blog.vo.Result;


public interface SysUserService {

   SysUser findUserByAccount(String account);

    SysUser getSysuserById(Long id);

    SysUser findUser(String account, String password);

    Result getUserInfoByToken(String token);

    void save(SysUser sysUser);

}
