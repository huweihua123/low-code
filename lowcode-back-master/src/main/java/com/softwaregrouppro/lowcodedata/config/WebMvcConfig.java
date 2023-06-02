package com.softwaregrouppro.lowcodedata.config;

import com.softwaregrouppro.lowcodedata.web.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc 配置
 *
 * @author smalljop
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * html静态资源   js静态资源    css静态资源
     */
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    /**
     * 配置本地文件上传的虚拟路径和静态化的文件生成路径
     * 备注：这是一种图片上传访问图片的方法，实际上也可以使用nginx反向代理访问图片
     *
     * @param registry
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //所有路径都被拦截
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
    }

}
