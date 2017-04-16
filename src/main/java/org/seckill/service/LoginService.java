package org.seckill.service;

import java.util.List;

import org.seckill.dto.SeckillResult;
import org.seckill.entity.Suser;

public interface LoginService {

	/**
	 * 根据名字和密码登录
	 * @param name
	 * @return
	 */
	SeckillResult<Suser> checkLogin(String name,String password);
	/**
	 * 用户注册
	 * @param user
	 * @return =1:注册成功
	 */
	SeckillResult<Integer> userLogin(Suser user);
	/**
	 * 根据用户名-更新用户信息
	 * @param user
	 * @return =1:更新成功
	 */
	SeckillResult<Integer> updateUser(Suser user);
	/**
	 * 
	 * @return
	 */
	SeckillResult<List<Suser>> getUserList();
	
}



