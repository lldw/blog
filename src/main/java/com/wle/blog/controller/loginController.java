package com.wle.blog.controller;

import com.wle.blog.service.LoginService;
import com.wle.blog.vo.Result;
import com.wle.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class loginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login( @RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }
}
