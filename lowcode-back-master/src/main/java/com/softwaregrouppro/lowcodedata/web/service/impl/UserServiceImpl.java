package com.softwaregrouppro.lowcodedata.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.softwaregrouppro.lowcodedata.constant.AccountRedisKeyConstants;
import com.softwaregrouppro.lowcodedata.utils.CacheUtils;
import com.softwaregrouppro.lowcodedata.utils.NameUtils;
import com.softwaregrouppro.lowcodedata.utils.Result;
import com.softwaregrouppro.lowcodedata.web.entity.UserEntity;
import com.softwaregrouppro.lowcodedata.web.mapper.UserMapper;
import com.softwaregrouppro.lowcodedata.web.request.AccountLoginRequest;
import com.softwaregrouppro.lowcodedata.web.request.RegisterAccountRequest;
import com.softwaregrouppro.lowcodedata.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户(AcUser)表服务实现类
 *
 * @author smalljop
 * @since 2020-11-10 18:10:43
 */
@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private final CacheUtils cacheUtils;

    @Override
    public Result emailRegister(RegisterAccountRequest request) {
        String code = cacheUtils.getTemp(StrUtil.format(AccountRedisKeyConstants.EMAIL_CODE, request.getEmail()));
        if (!request.getCode().equals(code)) {
            return Result.failed("验证码错误");
        }
        if (ObjectUtil.isNotNull(getUserByEmail(request.getEmail()))) {
            return Result.failed("该邮箱已经注册");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(request.getPassword());
        this.createUser(userEntity);
        return Result.success();
    }

    private void createUser(UserEntity userEntity) {
        userEntity.setName(NameUtils.getCnName());
//        userEntity.setAvatar(AccountConstants.DEFAULT_AVATAR);
        System.out.println(userEntity.getPassword());
        userEntity.setPassword(userEntity.getPassword());
        this.save(userEntity);
    }

    @Override
    public Result accountLogin(AccountLoginRequest request) {
        UserEntity userEntity;
        userEntity = getUserByEmail(request.getAccount());
        if (ObjectUtil.isNull(userEntity) || !userEntity.getPassword().equals(request.getPassword())) {
            System.out.println("error");
            return Result.failed("账号或密码错误");
        }
        return Result.success(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(final String email) {
        return this.getOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getEmail, email));
    }

    @Override
    public Boolean updatePassword(Long userId, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setPassword(password);
        return this.updateById(userEntity);
    }
}


