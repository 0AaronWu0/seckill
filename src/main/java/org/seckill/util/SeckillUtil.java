package org.seckill.util;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class SeckillUtil {
	
	private final  String slat = "skdjffji###!!&^&%GTWH@";
	private final  String slatString = "skdjffji#@!#!!&^&%@#@D5@";
	
	public  String getMD5(long seckillId) {
		String base = slat + seckillId + "/" ;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	public  String getPasswodMD5(String password) {
		String base = slatString + password + "/" ;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
	public  String createUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}
