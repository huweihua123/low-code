package com.softwaregrouppro.lowcodedata.utils;


import com.softwaregrouppro.lowcodedata.web.entity.UserEntity;

/**
 * 密码工具类
 */
public class PasswordUtils {


    /**
     * 加密密码
     *
     * @param password 密码
     * @return 加密后的密码
     */
//    public static String encode(String password) {
//        return passwordEncoder.encode(password);
//    }

    /**
     * 密码匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */


    /**
     * 检查密码是否正确
     */
    public static boolean checkPassword(UserEntity userEntity, String inputPassword) {

        return inputPassword.equals(userEntity.getPassword());
    }


}
