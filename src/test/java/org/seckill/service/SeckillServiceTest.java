package org.seckill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration(locations={
        "classpath:WEB-INF/application/spring-dao.xml",
        "classpath:WEB-INF/application/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills= seckillService.getSeckillList();
		logger.info("seckills={}",seckills);
	}

	@Test
	public void testGetSeckillById() {
		long seckillId = 1002;
		Seckill seckill = seckillService.getSeckillById(seckillId);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		long seckillId = 1000;
		Exposer exposer =seckillService.exportSeckillUrl(seckillId);
		logger.info("exposer={}", exposer);
	}

	@Test
	public void testExecuteSeckill() {
		long seckillId = 1000;
		long userPhone = 13022211112L;
		String md5="018bd79f607030689c3b18cd4c03f7e3";
		try {
			SeckillExecution seckillExecution =seckillService.executeSeckill(seckillId, userPhone, md5);
			logger.info("seckillExecution={}", seckillExecution);
		} catch (RepeatKillException e) {
			e.printStackTrace();
		} catch (SeckillCloseException e) {
			e.printStackTrace();
		} 
	}
	
	//集成测试代码完整逻辑，注意可重复执行
	@Test
	public void testSeckillLogic() {
		long seckillId = 1001;
		Exposer exposer =seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposed()){
			logger.info("exposer={}", exposer);
			long userPhone = 13022211113L;
			String md5=exposer.getMd5();
			try {
				SeckillExecution seckillExecution =seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("seckillExecution={}", seckillExecution);
			} catch (RepeatKillException e) {
				e.printStackTrace();
			} catch (SeckillCloseException e) {
				e.printStackTrace();
			} 
		}else{
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
	}
	
	@Test
	public void executeSeckillProcedure(){
		 long seckillId = 1003;
		 long userPhone = 14022221111L;
		 Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		 if(exposer.isExposed()){
			 String md5 = exposer.getMd5();
			 SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
			 logger.info(execution.getStateInfo());
		 }
	}
}


