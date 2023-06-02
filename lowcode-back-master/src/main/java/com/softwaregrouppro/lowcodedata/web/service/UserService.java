package com.softwaregrouppro.lowcodedata.web.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.softwaregrouppro.lowcodedata.utils.Result;
import com.softwaregrouppro.lowcodedata.web.entity.UserEntity;
import com.softwaregrouppro.lowcodedata.web.request.AccountLoginRequest;
import com.softwaregrouppro.lowcodedata.web.request.RegisterAccountRequest;


public interface UserService extends IService<UserEntity> {

    Result emailRegister(RegisterAccountRequest request);

    Result accountLogin(AccountLoginRequest request);

    UserEntity getUserByEmail(String email);

    Boolean updatePassword(Long userId, String password);
}
