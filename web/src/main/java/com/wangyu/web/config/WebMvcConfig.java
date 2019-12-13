package com.wangyu.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * webmvc配置
 *
 * @author wangyu
 * @date 2019/11/20 0:42
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.headImg.win_location}")
    private String winLocation;
    @Value("${upload.headImg.linux_location}")
    private String linuxLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      /*
       * 磁盘映射配置
       */
        if (!registry.hasMappingForPattern("/static/**")) {
            String property = System.getProperty("os.name");
            if (property.startsWith("Win")) {
              registry.addResourceHandler("/static/**").addResourceLocations("file:" + winLocation);
            } else {
              registry.addResourceHandler("/static/**")
                  .addResourceLocations("file:" + linuxLocation);
            }
        }
    }
}
