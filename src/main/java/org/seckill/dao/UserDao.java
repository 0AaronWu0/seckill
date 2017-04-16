package org.seckill.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Suser;

public interface UserDao {

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	int insertUser(Suser user);
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	int updateUser(Suser user);
	/**
	 * 根据用户名查询
	 * @param name
	 * @return
	 */
	Suser queryByName(String name);
	
	/**
	 * 根据描述模糊查询用户
	 * @param desc
	 * @return
	 */
	List<Suser> queryBydesc(String desc);
	/**
	 * 根据偏移和限制查找用户
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Suser> queryAllUser(@Param("offset")int offset,@Param("limit")int limit);

}
