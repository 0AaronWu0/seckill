package org.seckill.exception;
/**
 * 重复秒杀异常（运行期异常--不需要手动try Catch）
 * spring的事务只接收 运行期异常，并进行回滚。
 * @author THHINK
 *
 */
public class RepeatKillException extends SeckillException {

	public RepeatKillException(String message){
		super(message);
	}
	public RepeatKillException(String message,Throwable cause){
		super(message,cause);
	}

}

