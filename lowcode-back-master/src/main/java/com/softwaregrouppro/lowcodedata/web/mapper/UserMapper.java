package com.softwaregrouppro.lowcodedata.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.softwaregrouppro.lowcodedata.web.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户(AcUser)表数据库访问层
 *
 * @author smalljop
 * @since 2020-11-10 18:10:41
 */

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}