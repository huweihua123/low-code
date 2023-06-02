package com.softwaregrouppro.lowcodedata.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.softwaregrouppro.lowcodedata.group.AddGroup;
import com.softwaregrouppro.lowcodedata.group.UpdateGroup;
import com.softwaregrouppro.lowcodedata.utils.HtmlUtils;
import com.softwaregrouppro.lowcodedata.web.entity.enums.FormStatusEnum;
import com.softwaregrouppro.lowcodedata.web.entity.struct.BaseEntity;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.apache.ibatis.type.BooleanTypeHandler;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 用户表单表(Form)表实体类
 *
 * @author smalljop
 * @since 2020-11-18 18:16:16
 */
@Data
@FieldNameConstants
@TableName(value = "user_form", autoResultMap = true)
public class
UserFormEntity extends BaseEntity<UserFormEntity> {

    @NotBlank(message = "错误请求", groups = {UpdateGroup.class})
    private String formKey;

    @NotBlank(message = "表单名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    private String description;

    private Long userId;

    private FormStatusEnum status;

    @TableField(value = "is_deleted", typeHandler = BooleanTypeHandler.class)
    private Boolean deleted;

    public String getTextName() {
        // 标题是富文本 去除html 标签
        return HtmlUtils.cleanHtmlTag(name);
    }

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> stru;

}