package com.softwaregrouppro.lowcodedata.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class UserEntity {
    private Long id;
    private String password;
    private String name;
    private String avatar;
    private String email;
}
