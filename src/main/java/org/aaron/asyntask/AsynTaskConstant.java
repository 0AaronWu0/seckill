package org.aaron.asyntask;

import java.net.InetAddress;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class AsynTaskConstant {

	private static Logger logger = LoggerFactory.getLogger("AsynTaskConstant");
	//取任务线程启动后休眠时间
	public static final long TASK_THREAD_START_SLEEP_TIME = 6000L;
	
	//超时取任务线程启动后休眠时间
	public static final long TIMEOUT_THREAD_START_SLEEP_TIME = 6000L;
	
	public static final String CHARSET = "UTF-8";
	
	//获取IP
	public static String getIp(){
		String ip = null;
		try{
			ip = InetAddress.getLocalHost().getHostAddress();
		}catch (Exception e) {
			logger.error("获取本机IP失败，默认为空", e);
		}
		return ip;
	}
	
	//数据格式为utf-8 需要截取，截取采用简单模式length/3，
	public static String subStrForDB(String str,int length){
		String temp = str ;
		try{
			if(str.getBytes(CHARSET).length < length){
				return str;
			}
			for(int i=0; i<str.length(); i++){
				if(temp.getBytes(CHARSET).length <= length){
					break;
				}
				temp = temp.substring(0, temp.length() - i);
			}
		}catch (Exception e) {
			logger.error("截取UTF-8格式的字符异常", e);
		}
		return temp;
	}
	//从Spring 容器中获取对象
	public static Object getBean(String name){
		return ContextLoader.getCurrentWebApplicationContext().getBean(name);
	}
	//将对象转化为String
	public static String transObjectToStr(Object object){
		if(null != object){
			return object.toString();
		}else {
			return "";
		}
	}

}
