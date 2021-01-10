package com.weather.api.tiresias.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    private final String uploadImagePath;
//
//    public WebConfig(@Value("${custom.path.upload}") String uploadImagePath) {
//        this.uploadImagePath = uploadImagePath;
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/transcoding/**")
//                .addResourceLocations("file:/transcoding/");
    }

}
