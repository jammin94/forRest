package com.mvc.forrest.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

//@Configuration
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
   
   /*cors 때문에 추가*/
   
   @Override 
   public void addCorsMappings(CorsRegistry registry) { 
     registry
       .addMapping("/**") // 프로그램에서 제공하는 URL 
       .allowedOrigins("*") // 청을 허용할 출처를 명시, 전체 허용 (가능하다면 목록을 작성한다. 
       .allowedHeaders("*") // 어떤 헤더들을 허용할 것인지 
       .allowedMethods("*") // 어떤 메서드를 허용할 것인지 (GET, POST...) 
       .allowCredentials(true); // 쿠키 요청을 허용한다(다른 도메인 서버에 인증하는 경우에만 사용해야하며, true 설정시 보안상 이슈가 발생할 수 있다) 
       // .maxAge(1500) // preflight 요청에 대한 응답을 브라우저에서 캐싱하는 시간 ; 
   } 
 

}	
	
