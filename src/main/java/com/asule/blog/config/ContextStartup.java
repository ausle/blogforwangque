package com.asule.blog.config;

import com.asule.blog.modules.po.Options;
import com.asule.blog.modules.service.OptionsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class ContextStartup implements ApplicationRunner,ServletContextAware{

    private ServletContext servletContext;

    @Autowired
    private OptionsService optionsService;


    @Autowired
    private SiteOptions siteOptions;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("initialization ...");
        reloadOptions();
        log.info("OK, completed");
    }


    public void reloadOptions() {
        List<Options> dboptions = optionsService.findAll();

        Map<String, String> map = new HashMap<>();
        dboptions.forEach(opt -> {
            if (StringUtils.isNoneBlank(opt.getKey(), opt.getValue())) {
                map.put(opt.getKey(), opt.getValue());
            }
        });
        servletContext.setAttribute("options", map);

        Map<String, String> configOptions = siteOptions.getOptions();
        servletContext.setAttribute("site",configOptions);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext=servletContext;
    }
}
