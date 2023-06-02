package com.softwaregrouppro.lowcodedata.web.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormEntity;
import com.softwaregrouppro.lowcodedata.web.mapper.UserFormMapper;
import com.softwaregrouppro.lowcodedata.web.service.UserFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 表单主表(Form)表服务实现类
 *
 * @author smalljop
 * @since 2020-11-18 18:16:18
 */
@Slf4j
@Service("userFormService")
@RequiredArgsConstructor
public class UserFormServiceImpl extends ServiceImpl<UserFormMapper, UserFormEntity> implements UserFormService {

    @Override
    public UserFormEntity getByKey(final String key) {
        if (StrUtil.isBlank(key)) {
            return null;
        }
        return this.getOne(Wrappers.<UserFormEntity>lambdaQuery().eq(UserFormEntity::getFormKey, key));
    }
}