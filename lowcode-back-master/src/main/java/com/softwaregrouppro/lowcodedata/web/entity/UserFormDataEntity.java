package com.softwaregrouppro.lowcodedata.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.softwaregrouppro.lowcodedata.web.entity.struct.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

@Data
@Accessors(chain = true)
@FieldNameConstants
@TableName(value = "user_form_data", autoResultMap = true)
public class UserFormDataEntity extends BaseEntity implements Serializable {

    /**
     * 表单key
     */
    @NotBlank(message = "错误请求")
    private String formKey;


    /**
     * 提交序号
     */
    private Long serialNumber;

    /**
     * 填写结果原始数据
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> originalData;

    private String pin;




}
