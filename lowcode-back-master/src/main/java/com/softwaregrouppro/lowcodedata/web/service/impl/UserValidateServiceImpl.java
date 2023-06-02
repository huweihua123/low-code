package com.softwaregrouppro.lowcodedata.web.service.impl;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;

import com.softwaregrouppro.lowcodedata.constant.AccountRedisKeyConstants;
import com.softwaregrouppro.lowcodedata.constant.CommonConstants;
import com.softwaregrouppro.lowcodedata.utils.CacheUtils;
import com.softwaregrouppro.lowcodedata.web.service.MailService;
import com.softwaregrouppro.lowcodedata.web.service.UserValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : smalljop
 * @description :
 * @create : 2020-12-15 15:46
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserValidateServiceImpl implements UserValidateService {

    private final static String REG_EMAIL_TITLE = "注册验证码";
    private final static String RESET_PWD_EMAIL_TITLE = "重置密码";
    private final CacheUtils cacheUtils;
    private final MailService mailService;
//    private final SysEnvConfigService sysEnvConfigService;

    @Override
    public void sendEmailCode(String email) {
        String code = genValidateCode(StrUtil.format(AccountRedisKeyConstants.EMAIL_CODE, email));
        //发送邮件
        mailService.sendTemplateHtmlMail(email, REG_EMAIL_TITLE, "mail/reg-code", MapUtil.of("code", code));
    }

    /**
     * 生成验证码
     */
    private String genValidateCode(String key) {
        //生成验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", CommonConstants.ConstantNumber.FOUR);
        String code = randomGenerator.generate();
        cacheUtils.tempSave(key, code);
        log.debug("genValidateCode:{}", code);
        System.out.println(code);
        return code;
    }
}
