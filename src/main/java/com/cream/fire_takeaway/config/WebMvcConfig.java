package com.cream.fire_takeaway.config;

import com.cream.fire_takeaway.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Author: Cream
 * @Date: 2022-09-25-16:47
 * @Description:
 */

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    /**
     * 设置静态资源映射
     * @Param [registry]
     * @Return void
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开启静态资源映射！");
        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
    }


    @Override
    /**
     * 框架的消息转换器
     * @Param [converters]
     * @Return void
     */
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //追加到mvc中
        converters.add(0, messageConverter);
    }
}
