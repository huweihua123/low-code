package com.softwaregrouppro.lowcodedata.web.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReUtil;
import com.softwaregrouppro.lowcodedata.annotation.NotLogin;
import com.softwaregrouppro.lowcodedata.utils.Result;
import com.softwaregrouppro.lowcodedata.web.request.AccountLoginRequest;
import com.softwaregrouppro.lowcodedata.web.request.RegisterAccountRequest;
import com.softwaregrouppro.lowcodedata.web.service.UserService;
import com.softwaregrouppro.lowcodedata.web.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class UserLoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidateService userValidateService;
    @PostMapping("/login/account")
    public Result accoutLogin(@RequestBody AccountLoginRequest request, HttpServletRequest httpRequest){
        System.out.println("success");
//        System.out.println(userId);
        if (ReUtil.isMatch(Validator.MOBILE, request.getAccount())
                || ReUtil.isMatch(Validator.EMAIL, request.getAccount())) {
            return userService.accountLogin(request);
        }
        return Result.failed("账号错误，请输入手机号或邮箱");
    }

    @PostMapping("/register/email")
    @NotLogin
    public Result emailRegister(@RequestBody RegisterAccountRequest request) {
        return userService.emailRegister(request);
    }

    @GetMapping("/register/email/code")
    @NotLogin
    public Result sendEmailCode(@RequestParam String email) {
        Validator.validateEmail(email, "邮箱地址不正确");
        System.out.println("邮箱成功");
        userValidateService.sendEmailCode(email);
        return Result.success();
    }
}
