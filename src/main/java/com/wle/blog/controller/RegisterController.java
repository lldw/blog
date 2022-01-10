package com.wle.blog.controller;

import com.wle.blog.service.LoginService;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {

        return loginService.register(loginParams);
    }

}
