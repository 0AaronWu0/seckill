package org.seckill.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置spring和junit整合，junit启动是加载springIOC容器
 * @author THHINK
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration(locations={"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	//注入Dao实现类依赖
	@Resource
	private SeckillDao seckillDao;
	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill.getSname());
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		//java没有保存形参的记录，queryAll(@Param("offset")int offset,@Param("limit")int limit);
		List<Seckill> seckills = seckillDao.queryAll(0, 100);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);     
		}
		
	}
	@Test
	public void testReduceNumber() {
		Date killTime = new Date();
		int updateCount= seckillDao.reduceNumber(1001L, killTime);
		System.out.println("updateCount="+updateCount);
		/**
		 * update seckill SET number = number-1 WHERE seckill_id = ? and start_time <= ? and end_time >= ? and number > 0 
			Parameters: 1000(Long), 2016-12-02 22:18:56.631(Timestamp), 2016-12-02 22:18:56.631(Timestamp)
		 */
	}

}


