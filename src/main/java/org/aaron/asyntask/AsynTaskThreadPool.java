/**
 * 
 */
package org.aaron.asyntask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author as
 *
 */
public class AsynTaskThreadPool {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public SqlSessionTemplate sqlSession;
	//一个类维护队列
	private PriorityBlockingQueue<Runnable> asynTaskQueue = new PriorityBlockingQueue<Runnable>();
	private boolean running = true ;
	private ThreadPoolExecutor asynTaskServer;//真实的线程池
	private AsynTaskInfo asynTaskInfo;//线程池配置信息对象
	
	private AsynTaskMap asynTaskMap = new AsynTaskMap();
	
	private Map<String,Thread> threadMap =new HashMap<String, Thread>();
	
	public void addThreadMap(String threadId, Thread th){
		synchronized(threadMap){
			threadMap.put(threadId, th);
		}
	}
	
	public void clearThreadMap(){
		asynTaskMap.clear();
	}
	
	public Thread getThread(String threadName){
		if(threadMap.containsKey(threadName)){
			return threadMap.get(threadName);
		}else{
			return null ;
		}
	}
	
	public void initialize(){
		logger.info("线程池启动开始", asynTaskInfo);
		try{
			this.running = true ; 
			clearThreadMap();
			asynTaskServer = new ThreadPoolExecutor(asynTaskInfo.getServerCorePoolSize(),
					asynTaskInfo.getServerMaxPoolSize(), asynTaskInfo.getServerKeepAliveTime(), 
					TimeUnit.SECONDS, asynTaskQueue);
			logger.info("线程池启动成功", asynTaskInfo);
		}catch (Exception e) {
			logger.info("线程池启动失败", asynTaskInfo);
		}
	}
	
	public void terminate(){
		logger.info("线程池销毁开始", asynTaskInfo);
		this.running = false ;
		String mapper = asynTaskInfo.getMapper();
		if(asynTaskServer != null){
			try {
				asynTaskServer.shutdownNow();
			} catch (Exception e) {
				logger.error("线程池销毁异常",e);
			}
		}
		//清空线程集合中键值对
		clearThreadMap();
		//如果队列不位空，则把队列里的任务都重置
		synchronized(asynTaskMap){
			if(asynTaskQueue.size() > 0){
				HashMap<String,Object> map =new HashMap<String, Object>();
//				map.put("ip", AsynTaskConstant.getIp());
				map.put("taskType", asynTaskInfo.getTaskType());
				try {
					//TODO
					sqlSession.update(mapper, map);
				} catch (Exception e) {
					logger.info("线程池启动开始", asynTaskInfo);
				}
				asynTaskQueue.clear();
				asynTaskMap.clear();
			}
		}
		logger.info("线程池销毁结束", asynTaskInfo);
	}
	
	/**
	 * 是否繁忙
	 * @return
	 */
	public boolean isBusy(){
		if(this.asynTaskQueue.size() >= asynTaskInfo.getServerQueueLimit()){
			return true;
		}
		return false;
	}
	
	
	public void execute(AsynTaskWorker dat){
		synchronized (asynTaskMap) {
			asynTaskMap.addWorker(dat);
		}
		asynTaskServer.execute(dat);
	}
	
	public boolean removeQueueAndMap(String taskId){
		if(taskId == null || "".equals(taskId))
			return false;
		synchronized (asynTaskMap) {
			AsynTaskWorker taskWorker = asynTaskMap.getWorker(taskId);
			if(taskWorker != null){
				asynTaskQueue.remove(taskWorker);
				asynTaskMap.removeWorker(taskWorker);
				return true;
			}
			return false;
		}
	}
	
	public void removeMap(String taskId){
		synchronized (asynTaskMap) {
			asynTaskMap.removeWorker(taskId);
		}
	}
	
	public AsynTaskInfo getAsynTaskInfo(){
		return asynTaskInfo;
	}
	
	public void setAsynTaskInfo(AsynTaskInfo asynTaskInfo){
		this.asynTaskInfo = asynTaskInfo;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void setRunning(boolean running){
		this.running = running;
	}
}
