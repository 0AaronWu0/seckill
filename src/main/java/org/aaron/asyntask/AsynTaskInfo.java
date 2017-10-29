/**
 * 
 */
package org.aaron.asyntask;

import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author as
 *
 */
public class AsynTaskInfo {
	
	private String taskType;//任务类型
	private String mapper = "AsynTaskMapper"; //异步任务对应的Mapper文件
	private String updateTaskMapperName = mapper + "updateStateBeforeGetTask";
	private String taskListMapperName; //去任务ListMAPPer名
	
	private String taskThreadName;//任务线程名
	private int taskSleepTime = 5*1000;//取任务间隔时间
	private int taskNnum = 20 ;//每次取任务数量
	
	private String timeoutTaskThreadName;//超时任务线程名
	private int timeoutTaskSleepTime = 60 * 1000;//取任务间隔时间 ，默认60秒
	private int timeoutLimit = 6000 ;//超时时间限制
	
	private int serverCorePoolSize = 3;//最小线程数
	private int serverMaxPoolSize  = 3;//最大线程数，默认等于最小线程数
	private long serverKeepAliveTime = 300 ;//空闲线程保留时间
	private int serverQueueLimit;//队列阈值
	private String serverName;//服务线程池名
	
	private int redoNum = 3;//重做次数
	private int redoTimeSplit = 0;//重做间隔
	
	private String receiveThreadName;//接收消息线程（）
	private String queueid;//队列ID
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	//获取异步任务主键（可自定义）
	public String getTaskId(Map<String,Object> map){
		return sqlSession.selectOne(mapper + "selectTaskId");
	}
	//封装取任务参数（可自定义）
	public void setParamMap(Map<String,Object> map){

	}
	
	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}
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
	public int getTimeoutLimit() {
		return timeoutLimit;
	}
	public void setTimeoutLimit(int timeoutLimit) {
		this.timeoutLimit = timeoutLimit;
	}
	public int getRedoNum() {
		return redoNum;
	}
	public void setRedoNum(int redoNum) {
		this.redoNum = redoNum;
	}
	public int getRedoTimeSplit() {
		return redoTimeSplit;
	}
	public void setRedoTimeSplit(int redoTimeSplit) {
		this.redoTimeSplit = redoTimeSplit;
	}
	
}
