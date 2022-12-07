package com.ContentMgtSystem.Blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public final static String IMAGE_RESOURCE_BASE = "images/";
    public final static String IMAGE_FILE_BASE = "images/";
    public final static String BASE_URL = "http://localhost:8080";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(IMAGE_RESOURCE_BASE + "**")
                .addResourceLocations("file:" + IMAGE_FILE_BASE);
    }
}