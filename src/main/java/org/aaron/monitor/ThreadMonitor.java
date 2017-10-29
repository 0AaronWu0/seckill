package org.aaron.monitor;

public class ThreadMonitor {

	private static final ThreadMonitor instance = new ThreadMonitor();
	
	public static ThreadMonitor getInstance() {
		return instance;
	}

	public void addMonitoredThread(String taskThreadName, Object asynTaskGetTaskThread) {
		
	}

}
