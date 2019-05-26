package com.asule.blog.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.asule.blog.modules.template.TemplateDirective;
import com.asule.blog.modules.template.method.TimeAgoMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.Map;

@EnableAsync
@Configuration
public class SiteConfiguration {


    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private ApplicationContext applicationContext;


    @PostConstruct
    public void setSharedVariable() {
        Map<String, TemplateDirective> map = applicationContext.getBeansOfType(TemplateDirective.class);
        map.forEach((k, v) -> configuration.setSharedVariable(v.getName(), v));
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
//        configuration.setSharedVariable("shiro", new ShiroTags());
    }



    @Bean
    @ConditionalOnClass({JSON.class})
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,    //不输出为null的字段
                SerializerFeature.WriteNullStringAsEmpty,//若为null，输出""
                SerializerFeature.WriteNullListAsEmpty, //list为null,输出[]
                SerializerFeature.DisableCircularReferenceDetect
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }

}
