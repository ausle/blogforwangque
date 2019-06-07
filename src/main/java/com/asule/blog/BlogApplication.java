package com.asule.blog;

import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


/*构建war文件，需要WebApplicationInitializer*/



@EnableCaching	/*开启spring-boot缓存，springboot中的缓存注解@Cacheable，@CachePut等就可以使用*/
@SpringBootApplication
public class BlogApplication{

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}









	/*配置启动类*/
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(BlogApplication.class);
//	}
}
