package org.seckill.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Suser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration(locations={
        "classpath:WEB-INF/application/spring-dao.xml",
        "classpath:WEB-INF/application/spring-service.xml"})
public class LoginServiceTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Resource
	private LoginService loginService;
	
	@Test
	public void testCheckLogin() {
		SeckillResult<Suser> seckillResult= loginService.checkLogin("小李", "1111");
		if(!seckillResult.isSuccess()){
			logger.warn("user={}",seckillResult.getError());
		}else{		
			logger.info("user={}",seckillResult.getData());
		}
	}

	@Test
	public void testUserLogin() {
		Suser user = new Suser("大李", "1111", "233@qq.com", "13022221111",
				"10.133.13.12", "00");
		SeckillResult<Integer> seckillResult = loginService.userLogin(user);
		if(!seckillResult.isSuccess()){
			logger.warn("user={}",seckillResult.getError());
		}else{
			logger.info("user={}",seckillResult.getData());
		}
	}

	@Test
	public void testUpdateUser() {
		Suser user = new Suser("大李", "1111", "ddd@qq.com", "13022221111",
				"10.133.13.12", "00");
		SeckillResult<Integer> seckillResult = loginService.updateUser(user);
		if(!seckillResult.isSuccess()){
			logger.warn("user={}",seckillResult.getError());
		}else{
			logger.info("user={}",seckillResult.getData());
		}
	}

	@Test
	public void testGetUserList() {
		SeckillResult<List<Suser>> seckillResult = loginService.getUserList();
		if(seckillResult.isSuccess()){
			logger.info("users={}",seckillResult.getData());
		}
	}

}
