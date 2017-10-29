package org.aaron.asyntask;

import org.aaron.monitor.ThreadStatus;

/**
 * 线程父类
 * 用于线程监控
 */
public abstract class AbstractAsynTask {

	public abstract ThreadStatus getStatus();
	
}
