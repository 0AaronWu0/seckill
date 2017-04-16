package org.seckill.dao;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Suser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration(locations={"classpath:spring/spring-dao.xml"})
public class UserDaoTest {

	//注入Dao实现类依赖
	@Resource
	private UserDao userDao;
	
	@Test
	public void testInsertUser() {
		Suser user = new Suser( "小王", "2222", "kij@ee.com", "13333322341", 
				"口令2", "描述3");
		user.setUserId("qweeqw");
		Integer count = userDao.insertUser(user);
		System.out.println("插入记录数："+ count);
	}

	@Test
	public void testUpdateUser() {
		Suser user = new Suser("老王", null, null,null, 
				"口令4", null);
		int count = userDao.updateUser(user);
		System.out.println("更新记录数："+ count);
	}

	@Test
	public void testQueryByName() {
		String name = "张五";
		Suser user = userDao.queryByName(name);
		System.out.println(name +":"+user);
	}
	
	@Test
	public void testQueryBydesc() {
		String desc = "2";
		List<Suser> users = userDao.queryBydesc(desc);
		for (Suser user : users) {
			System.out.println(user);
		}
	}	

	@Test
	public void testQueryAllUser() {
		List<Suser> users = userDao.queryAllUser(0, 10);
		for (Suser user : users) {
			System.out.println("用户："+user);
		}
	}

}
