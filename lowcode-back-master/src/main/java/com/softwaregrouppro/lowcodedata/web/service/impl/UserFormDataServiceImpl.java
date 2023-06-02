package com.softwaregrouppro.lowcodedata.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormDataEntity;
import com.softwaregrouppro.lowcodedata.web.mapper.UserFormDataMapper;
import com.softwaregrouppro.lowcodedata.web.service.UserFormDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 表单表单项(FormResult)表服务实现类
 *
 * @author smalljop
 * @since 2020-11-23 14:09:22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserFormDataServiceImpl extends ServiceImpl<UserFormDataMapper, UserFormDataEntity> implements UserFormDataService {

}
