package com.asule.blog.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "site")
@Configuration
public class SiteOptions{

    /**
     * 系统版本号
     */
    private String version;

    /**
     * 运行文件存储路径
     */
    private String location;


    private Map<String, String> options = new HashMap<>();



    public Integer getIntegerValue(String key){
        return Integer.parseInt(options.get(key));
    }


    public String getValue(String key){
        return options.get(key);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}
