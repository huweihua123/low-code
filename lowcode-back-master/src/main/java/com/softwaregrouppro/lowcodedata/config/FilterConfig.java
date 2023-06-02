package com.softwaregrouppro.lowcodedata.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author smalljop
 * @description 过滤器配置
 * @create 2019-01-29 16:27
 **/
@Configuration
@RequiredArgsConstructor
public class FilterConfig {


    private final PlatformSignProperties platformSignProperties;


    /**
     * 跨域过滤器
     *
     * @return
     */

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        // 允许跨域的头部信息
        config.addAllowedHeader("*");
        // 允许跨域的方法
        config.addAllowedMethod("*");
        // 可访问的外部域
        config.addAllowedOrigin("*");
        // 需要跨域用户凭证（cookie、HTTP认证及客户端SSL证明等）
        //config.setAllowCredentials(true);
        //config.addAllowedOriginPattern("*");

        // 跨域路径配置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }



    /**
     * 请求日志
     *
     * @return
     */


    /**
     * 验证码过滤器
     *
     * @return
     */


}
