package com.itai.itaiJavaPlay.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.itai.itaiJavaPlay.interceptor.LayoutModelInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // helps us set the highlight in the menu
    registry.addInterceptor(new LayoutModelInterceptor());
  }
}