package com.softwaregrouppro.lowcodedata.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softwaregrouppro.lowcodedata.web.entity.UserFormEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 表单表(Form)表数据库访问层
 *
 * @author smalljop
 * @since 2020-11-18 18:16:17
 */
@Mapper
public interface UserFormMapper extends BaseMapper<UserFormEntity> {

}