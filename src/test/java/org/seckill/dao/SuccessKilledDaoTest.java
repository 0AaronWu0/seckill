package org.seckill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration(locations={"classpath:WEB-INF/application/spring-dao.xml"})
public class SuccessKilledDaoTest {

	//注入Dao实现类依赖
	@Resource
	private SuccessKilledDao successKilledDao;
	@Test
	public void testInsertSuccessKilled() {
		long seckillId=1000L;
		long userPhone=13012311111L;
		int insertcount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println("insertcount="+insertcount);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		long seckillId=1000L;
		long userPhone=13012311111L;
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(successKilled );
		System.out.println(successKilled.getSeckill() );
	}

}
