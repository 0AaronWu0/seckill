package org.seckill.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.seckill.dao.UserDao;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Suser;
import org.seckill.service.LoginService;
import org.seckill.util.SeckillUtil;
import org.springframework.stereotype.Service;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
	//注入Dao实现类依赖
	@Resource
	private UserDao userDao;
	@Resource
	private SeckillUtil seckillUtil;
	
	public SeckillResult<Suser> checkLogin(String name,String password) {
		Suser user = userDao.queryByName(name);
		if(user==null){
		return new SeckillResult<Suser>(false, "用户不存在");
		}
		if(password.length()!=32){
			String uuid = user.getUserId();
			password = seckillUtil.getPasswodMD5(uuid+password);
		}
		if(!user.getUserPassword().equals(password)){
			return new SeckillResult<Suser>(false, "密码不正确");
			}
		return new SeckillResult<Suser>(true,userDao.queryByName(name));
	}
	
	public SeckillResult<Integer> userLogin(Suser user) {
		String uuid = seckillUtil.createUuid();
		user.setUserId(uuid);//生成uuid
		//密码加密
		String password = seckillUtil.getPasswodMD5(uuid+user.getUserPassword());
		user.setUserPassword(password);
		return new SeckillResult<Integer>(true,userDao.insertUser(user));
	}

	public SeckillResult<Integer> updateUser(Suser user) {
		Suser user1 = userDao.queryByName(user.getUserName());
		String uuid = user1.getUserId();
		String password = seckillUtil.getPasswodMD5(uuid+user.getUserPassword());
		user.setUserPassword(password);
		return new SeckillResult<Integer>(true,userDao.updateUser(user));
	}

	public SeckillResult<List<Suser>> getUserList() {
		return new SeckillResult<List<Suser>>(true,userDao.queryAllUser(0, 30));
	}

}
