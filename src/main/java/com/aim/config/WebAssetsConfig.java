package com.aim.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @AUTO 资源访问配置
 * @Author AIM
 * @DATE 2018/10/22
 */
@EnableWebMvc
@Configuration
public class WebAssetsConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.mvc.view.suffix}")
    private String suffix;
    @Value("${spring.mvc.view.prefix}")
    private String prefix;

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix(prefix);
        resolver.setSuffix(suffix);
        resolver.setCache(true);
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

//    @Bean
//    public VelocityViewResolver getVelocityViewResolver() {
//        VelocityViewResolver velocityViewResolver = new VelocityViewResolver();
//        velocityViewResolver.setCache(true);
//        velocityViewResolver.setPrefix("");
//        velocityViewResolver.setSuffix(".html");
//        velocityViewResolver.setRequestContextAttribute("request");
//        velocityViewResolver.setOrder(0);
//        velocityViewResolver.setContentType("text/html;charset=UTF-8");
//
//        return velocityViewResolver;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/assets/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("favicon.ico").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
