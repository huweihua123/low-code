package com.softwaregrouppro.lowcodedata.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormEntity;

/**
 * 表单表(Form)表服务接口
 *
 * @author smalljop
 * @since 2020-11-18 18:16:18
 */
public interface UserFormService extends IService<UserFormEntity> {
    UserFormEntity getByKey(final String key);
}