package com.mvc.forrest.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value("${file.path}")
	private String fileRealPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);

	    List<String> imageFolders = Arrays.asList("content", "gif","header","uploadFiles");
	    for(String imageFolder : imageFolders) {
	      registry.addResourceHandler("/images/" +imageFolder +"/**")
	        .addResourceLocations("file:///" + fileRealPath + imageFolder +"/")
	        .setCachePeriod(3600)
	        .resourceChain(true)
	        .addResolver(new PathResourceResolver());
	    }
	  }

}	
	
