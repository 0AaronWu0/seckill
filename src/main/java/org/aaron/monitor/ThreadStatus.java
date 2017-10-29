package org.aaron.monitor;

import java.util.Date;

public class ThreadStatus {

	private String threadName;
	private String taskType;
	private String ThreadPoolIp;
	private Date lastReportTime;
	
	
	public String getThreadName() {
		return threadName;
	}
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getThreadPoolIp() {
		return ThreadPoolIp;
	}
	public void setThreadPoolIp(String threadPoolIp) {
		ThreadPoolIp = threadPoolIp;
	}
	public Date getLastReportTime() {
		return lastReportTime == null ? null: (Date)lastReportTime.clone();
	}
	public void setLastReportTime(Date lastReportTime) {
		this.lastReportTime = lastReportTime == null ? null: (Date)lastReportTime.clone();
	}
	
}
