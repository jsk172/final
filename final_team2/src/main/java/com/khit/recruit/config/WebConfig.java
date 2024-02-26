package com.khit.recruit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	//  /file:///C:/springfiles/ 경로를 /upload/ 로 매핑
	String resourcePath = "/upload/**";
	String savePath = "file:///C:/springfiles/";
	
	//매서드 재정의
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//파일의 경로 설정
		registry.addResourceHandler(resourcePath)  //view에 접근할 경로
		        .addResourceLocations(savePath);   //실제 저장 파일 위치
	}
}
