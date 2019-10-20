package com.bee.sample.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	//开启矩阵变量
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }
    //拦截器3.6.1
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new SessionHandlerInterceptor()).addPathPatterns("/exampleone/m31");
    }
    //跨域访问 与响应头属性'Access-Control-Allow-Origin'有关
    public void addCorsMapping(CorsRegistry registry) {
    	/*registry.addMapping("/**"); //允许所有域跨域访问
    	registry.addMapping("/api/**")
    	            .allowedOrigins("http://domain2.com")
    	            .allowedMethods("POST","GET");*/
    	registry.addMapping("http://www.baidu.com"); 
    	
    	
    }
    //格式化
    public void addFormatters(FormatterRegistry registry) {
    	registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }
    //拦截器
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("/index.html");
    	registry.addRedirectViewController("/**/*.do", "/index.html");
    }
    //会话拦截器
    
    class SessionHandlerInterceptor implements HandlerInterceptor{
    	@Override
    	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler)throws Exception{
    		System.out.println("preHandle");// 调用Controller方法之前调用此方法
    		return true;
    	}
    	@Override
    	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    			 ModelAndView modelAndView) throws Exception{
    		System.out.println("postHandle");// 调用Controller方法之后,页面渲染前调用此方法
    	}
    	@Override
    	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object Handler, Exception ex)throws Exception{
    		System.out.println("afterCompletion");// 调用Controller方法之后,页面渲染后调用此方法
    	}
    }
}
