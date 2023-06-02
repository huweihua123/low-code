package com.softwaregrouppro.lowcodedata.web.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.Map;

@Data
public class UserFormStruVO {
    private String FormKey;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String,Object> stru;

}
