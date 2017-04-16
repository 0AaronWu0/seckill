package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * 业务接口：站在“使用者”角度设计接口，
 * 三个方面：方法定义的粒度（明确使用者要使用的方法），
 * 参数（简单直接，少用Map），
 * 返回类型（return 类型简单，少用Map /异常）
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * @return
	 */
	List<Seckill> getSeckillList();
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 */
	Seckill getSeckillById(long seckillId);
	/**
	 * 秒杀开启时输出渺少接口地址，
	 * 否则输出系统时间和秒杀时间
	 * @param seckillId
	 * 	 @return Exposer
	 */
	Exposer exportSeckillUrl(long seckillId);
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return SeckillExecution
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
		throws SeckillException,RepeatKillException,SeckillCloseException;
	
	/**
	 * 执行秒杀操作by存储过程
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return SeckillExecution
	 */
	SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5);
}


