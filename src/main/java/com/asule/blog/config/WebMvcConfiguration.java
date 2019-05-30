package com.asule.blog.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.asule.blog.intercepter.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{


    @Autowired
    private BaseInterceptor baseInterceptor;

    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;


    @Autowired
    private SiteOptions siteOptions;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*配置拦截器拦截什么路径以及不拦截什么路径*/
        registry.addInterceptor(baseInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/dist/**", "/store/**", "/static/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String location = "file:///" + siteOptions.getLocation();

        registry.addResourceHandler("/dist/**")
                .addResourceLocations("classpath:/static/dist/");

        registry.addResourceHandler("/storage/avatars/**")
                .addResourceLocations(location + "/storage/avatars/");
        registry.addResourceHandler("/storage/thumbnails/**")
                .addResourceLocations(location + "/storage/thumbnails/");
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }

}
