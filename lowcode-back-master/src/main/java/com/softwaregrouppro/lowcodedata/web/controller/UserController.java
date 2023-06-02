package com.softwaregrouppro.lowcodedata.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.softwaregrouppro.lowcodedata.utils.PasswordUtils;
import com.softwaregrouppro.lowcodedata.utils.Result;
import com.softwaregrouppro.lowcodedata.web.entity.UserEntity;
import com.softwaregrouppro.lowcodedata.web.entity.vo.UserDetailVO;
import com.softwaregrouppro.lowcodedata.web.request.UpdateUserRequest;
import com.softwaregrouppro.lowcodedata.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/current/detail")
    public Result queryCurrentUser(@RequestAttribute Long userId) {
        System.out.println(userId);
        UserEntity userEntity = userService.getById(userId);
        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtil.copyProperties(userEntity, userDetailVO);
        return Result.success(userEntity);
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody UserEntity userEntity, @RequestAttribute Long userId) {
        System.out.println(userEntity.toString());
        System.out.println(userId);
        userEntity.setId(userId);
        return Result.success(userService.updateById(userEntity));
    }

    @PostMapping("/update/password")
    public Result updatePassword(@RequestBody UpdateUserRequest.Password request, @RequestAttribute Long userId) {
        UserEntity userEntity=userService.getById(userId);
        System.out.println(request.toString());
        System.out.println(userEntity.toString());
        if (!PasswordUtils.checkPassword(userEntity, request.getOldPassword())) {
            return Result.failed("旧密码错误");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            return Result.failed("两次密码不一致");
        }
        userService.updatePassword(userEntity.getId(), request.getPassword());
        return Result.success();
    }
}
