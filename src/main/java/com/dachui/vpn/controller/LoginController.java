package com.dachui.vpn.controller;

import com.dachui.vpn.service.LoginService;
import com.dachui.vpn.common.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 11:34
 * @Description:
 */
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<String> verificationLogin(String userName, String passWord) {
        try {
            return Result.success(loginService.Login(userName, passWord));
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

}
