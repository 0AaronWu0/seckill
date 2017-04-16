package org.seckill.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seckill.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		@Resource
		private LoginService loginService;
	
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
	        logger.info("==============执行顺序: 1、preHandle================");    
	        String requestUri = request.getRequestURI();  
	        String contextPath = request.getContextPath();  
	        String url = requestUri.substring(contextPath.length());  
	        
	        logger.info("requestUri:"+requestUri);    
	        logger.info("contextPath:"+contextPath);    
	        logger.info("url:"+url);    
	          
	        String username =  (String)request.getSession().getAttribute("user");   
	        if(username != null){  
	        	logger.info("Interceptor：跳转到login页面！");  
	            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);  
	            return false;  
	        }else  
	            return true;  			
		}
		//请求后，modelAndView:返回的视图 .setViewName（“/视图.jsp”）
		//ModelAndView对象可以改变发往的视图或修改发往视图的信息。
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {
			logger.info("==============执行顺序: 2、postHandle================");    
	        if(modelAndView != null){  //加入当前时间    
	            modelAndView.addObject("var", "测试postHandle");    
	        }   
	        //modelAndView.setViewName("course_admin/file");
		}

		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
				Exception ex) throws Exception {
			logger.info("==============执行顺序: 3、afterCompletion================");
		}
		
}



