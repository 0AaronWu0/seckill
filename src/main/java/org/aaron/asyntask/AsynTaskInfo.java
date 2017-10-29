/**
 * 
 */
package org.aaron.asyntask;

/**
 * @author as
 *
 */
public class AsynTaskInfo {
	
	private String taskType;//任务类型
	private String mapper;//异步任务对应的Mapper文件
	private String updateTaskMapperName;
	private String taskListMapperName;
	private String taskThreadName;//任务线程名
	private int taskSleepTime;//取任务间隔时间
	private int taskNnum;//每次取任务数量
	
	private String timeoutTaskThreadName;//超时任务线程名
	private int timeoutTaskSleepTime;//取任务间隔时间
	
	private String receiveThreadName;//接收消息线程（）
	private String queueid;//队列ID
	
	private int serverCorePoolSize;//最小线程数
	private int serverMaxPoolSize;//最大线程数，默认等于最小线程数
	private long serverKeepAliveTime = 300 ;//空闲线程保留时间
	private int serverQueueLimit;//队列阈值
	private String serverName;//服务线程名
	
	
	public String getUpdateTaskMapperName() {
		return updateTaskMapperName;
	}
	public void setUpdateTaskMapperName(String updateTaskMapperName) {
		this.updateTaskMapperName = updateTaskMapperName;
	}
	public String getTaskListMapperName() {
		return taskListMapperName;
	}
	public void setTaskListMapperName(String taskListMapperName) {
		this.taskListMapperName = taskListMapperName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getMapper() {
		return mapper;
	}
	public void setMapper(String mapper) {
		this.mapper = mapper;
	}
	public String getTaskThreadName() {
		return taskThreadName;
	}
	public void setTaskThreadName(String taskThreadName) {
		this.taskThreadName = taskThreadName;
	}
	public int getTaskSleepTime() {
		return taskSleepTime;
	}
	public void setTaskSleepTime(int taskSleepTime) {
		this.taskSleepTime = taskSleepTime;
	}
	public int getTaskNnum() {
		return taskNnum;
	}
	public void setTaskNnum(int taskNnum) {
		this.taskNnum = taskNnum;
	}
	public String getTimeoutTaskThreadName() {
		return timeoutTaskThreadName;
	}
	public void setTimeoutTaskThreadName(String timeoutTaskThreadName) {
		this.timeoutTaskThreadName = timeoutTaskThreadName;
	}
	public int getTimeoutTaskSleepTime() {
		return timeoutTaskSleepTime;
	}
	public void setTimeoutTaskSleepTime(int timeoutTaskSleepTime) {
		this.timeoutTaskSleepTime = timeoutTaskSleepTime;
	}
	public String getReceiveThreadName() {
		return receiveThreadName;
	}
	public void setReceiveThreadName(String receiveThreadName) {
		this.receiveThreadName = receiveThreadName;
	}
	public String getQueueid() {
		return queueid;
	}
	public void setQueueid(String queueid) {
		this.queueid = queueid;
	}
	public int getServerCorePoolSize() {
		return serverCorePoolSize;
	}
	public void setServerCorePoolSize(int serverCorePoolSize) {
		this.serverCorePoolSize = serverCorePoolSize;
		this.serverMaxPoolSize = serverCorePoolSize;
	}
	public int getServerMaxPoolSize() {
		return serverMaxPoolSize;
	}
	public void setServerMaxPoolSize(int serverMaxPoolSize) {
		this.serverMaxPoolSize = serverMaxPoolSize;
	}
	public long getServerKeepAliveTime() {
		return serverKeepAliveTime;
	}
	public void setServerKeepAliveTime(long serverKeepAliveTime) {
		this.serverKeepAliveTime = serverKeepAliveTime;
	}
	public int getServerQueueLimit() {
		return serverQueueLimit;
	}
	public void setServerQueueLimit(int serverQueueLimit) {
		this.serverQueueLimit = serverQueueLimit;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	
	
}
