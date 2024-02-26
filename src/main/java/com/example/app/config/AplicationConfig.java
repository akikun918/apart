package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.app.filter.AdminFilter;

@Configuration
public class AplicationConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:///C:/Users/zd3M01/uploads/");
	}

	// 認証用フィルタの有効化
	@Bean
	FilterRegistrationBean<AdminFilter> authFilter() {
		var bean = new FilterRegistrationBean<AdminFilter>(new AdminFilter());
		bean.addUrlPatterns("/admin/*");
		return bean;
	}

	//↓うまくいかなかった
//	// 認証用フィルタの有効化
//	@Bean
//	FilterRegistrationBean<RoomEditFilter> roomEditFilter() {
//		var bean = new FilterRegistrationBean<RoomEditFilter>(new RoomEditFilter());
//		bean.addUrlPatterns("/*");
//		return bean;
//	}

}
