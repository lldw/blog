package com.wle.blog.service.iml;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wle.blog.mapper.SysUserMapper;
import com.wle.blog.pojo.SysUser;
import com.wle.blog.service.LoginService;
import com.wle.blog.service.SysUserService;
import com.wle.blog.vo.ErrorCode;
import com.wle.blog.vo.LoginUserVo;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    @Lazy
    private LoginService loginService;

    /**
     * 查询用户是否存在
     *
     * @param account
     * @return
     */

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser getSysuserById(Long authorId) {
        SysUser sysUser = userMapper.selectById(authorId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("wll");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname);
        queryWrapper.last("limit 1");
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */

    @Override
    public Result getUserInfoByToken(String token) {
        SysUser user = this.loginService.checkToken(token);
        if (user == null) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    /**
     * 保存用户
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        this.userMapper.insert(sysUser);

    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser user = userMapper.selectById(authorId);
        if (user == null) {
            user = new SysUser();
            user.setNickname("wll");
            user.setAvatar("");
            user.setId(1L);
        }
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setAvatar(user.getAvatar());
        userVo.setNickname(user.getNickname());
        return userVo;
    }
}
