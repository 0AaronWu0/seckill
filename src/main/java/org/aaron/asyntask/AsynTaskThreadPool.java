/**
 * 
 */
package org.aaron.asyntask;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author as
 *
 */
public class AsynTaskThreadPool {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public SqlSessionTemplate sqlSession;
	//一个类维护队列,PriorityBlockingQueue 优先级队列,里面存储的对象必须是实现Comparable接口
	//规则是：当前和其他对象比较，如果compare方法返回负数，那么在队列里面的优先级就比较搞
	private PriorityBlockingQueue<Runnable> asynTaskQueue = new PriorityBlockingQueue<Runnable>();
	private volatile boolean running = true ;
	private ThreadPoolExecutor asynTaskThreadPool;//真实的线程池
	private AsynTaskInfo asynTaskInfo;//线程池配置信息对象
	//存储队列中的worker集合，用于超时任务移除队列中的Worker
	private AsynTaskMap asynTaskMap = new AsynTaskMap();
	//存储线程池中的线程的集合，用于超时任务杀死线程
	private Map<String,Thread> threadMap =new HashMap<String, Thread>();
	
	public void addThreadMap(String threadId, Thread th){
		synchronized(threadMap){
			threadMap.put(threadId, th);
		}
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
			threadMap.clear();
			asynTaskMap.clear();
			asynTaskThreadPool = new ThreadPoolExecutor(asynTaskInfo.getServerCorePoolSize(),
					asynTaskInfo.getServerMaxPoolSize(), asynTaskInfo.getServerKeepAliveTime(), 
					TimeUnit.SECONDS, asynTaskQueue);
			AsynTaskThreadPoolManager.addPool(asynTaskInfo.getTaskType(),this);
			logger.info("线程池启动成功", asynTaskInfo);
		}catch (Exception e) {
			logger.info("线程池启动失败", asynTaskInfo);
		}
	}
	
	
	/**
	 * 销毁方法
	 */
	public void terminate(){
		logger.info("线程池销毁开始", asynTaskInfo);
		this.running = false ;
		String mapper = asynTaskInfo.getMapper();
		if(asynTaskThreadPool != null){
			try {
				asynTaskThreadPool.shutdownNow();
			} catch (Exception e) {
				logger.error("线程池销毁异常",e);
			}
		}
		//清空线程集合中键值对
		threadMap.clear();
		//如果队列不位空，则把队列里的任务都重置
		synchronized(asynTaskMap){
			if(asynTaskQueue.size() > 0){
				HashMap<String,Object> map =new HashMap<String, Object>();
				map.put("ip", AsynTaskConstant.getIp());
				map.put("taskType", asynTaskInfo.getTaskType());
				try {
					sqlSession.update(mapper+"updateMyIpTaskStateToPedding", map);
				} catch (Exception e) {
					logger.info("线程池启动开始", asynTaskInfo);
				}
				asynTaskQueue.clear();
				asynTaskMap.clear();
			}
		}
		logger.info("线程池销毁结束", asynTaskInfo);
	}
	
	public void removeMap(String taskId){
		synchronized (asynTaskMap) {
			asynTaskMap.removeWorker(taskId);
		}
	}

	/**
	 * 移除队列，任务超时时调用，仅仅移除队列，任务状态有调用方修改为超时被杀
	 * @param taskId
	 * @return
	 */
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

	/**
	 * 移除优先级最低的并小于传入优先级的task,同时把任务状态修改为待处理
	 * @param priotityType
	 * @return
	 */
	public boolean removeLowestPriorityWorker(AsynTaskEnum.TaskPriotityType priotityType){
		logger.info("asynTaskThreadPool最低优先级移除开始", asynTaskInfo);
		synchronized (asynTaskMap){
			AsynTaskWorker lowerWorker = asynTaskMap.getLowestWorker();
			if (lowerWorker != null && priotityType.getValue().intValue() >lowerWorker.getPriority()){
				try {
					sqlSession.update(asynTaskInfo.getMapper() + ".updateTaskStateToPedding",lowerWorker.getAsynTaskBean());
				}catch (Exception e){
					logger.error("asynTaskThreadPool最低优先级移除异常",e);
					return false;
				}
				asynTaskQueue.remove(lowerWorker);
				asynTaskMap.removeWorker(lowerWorker);
				return true;
			}
		}
		return false;
	}

	public void execute(AsynTaskWorker dat){
		synchronized (asynTaskMap) {
			asynTaskMap.addWorker(dat);
		}
		asynTaskThreadPool.execute(dat);
	}

	/**
	 * 添加异步任务，任务编号自动生成
	 * @param taskBean
	 * @return 返回插入任务条数，成功返回1条
	 */
	public int addAsynTask(AsynTaskBean taskBean,boolean transactionFlag) {
		if(!isRunning()){
			return insertPeddingTask(taskBean);
		}
		//如线程池繁忙，插入异步任务，如果空闲，插入队列任务，并放入队列
		if (taskBean.getPlanTime() == null && taskBean.getPlanTimeSeconds() == null) {
			if (!isBusy()) {
				return insertInQueueTask(taskBean,transactionFlag);
			} else {
				return insertPeddingTask(taskBean);
			}
		} else {
			return insertPeddingTask(taskBean);
		}

	}

	/**
	 * 添加异步任务，所有任务都是进异步任务
	 * @param asynTaskBean
	 * @return
	 */
	public int addOnlyAsynTask(AsynTaskBean asynTaskBean) {
		return insertPeddingTask(asynTaskBean);
	}

	/**
	 * 添加异步任务，优先级设为最高,移除低优先级的并执行，任务编号自动生成
	 * @param taskBean
	 * @return 返回插入任务条数，成功返回1条
	 */
	public int addOnlineTask(AsynTaskBean taskBean,boolean transactionFlag) {
		if(!isRunning()){
			return insertPeddingTask(taskBean);
		}
		//如线程池繁忙，插入异步任务，如果空闲，插入队列任务，并放入队列
		if (taskBean.getPlanTime() == null && taskBean.getPlanTimeSeconds() == null) {
			taskBean.setPriority(AsynTaskEnum.TaskPriotityType.FIRST.getValue());//设置优先级最高
			if (!isBusy()) {
				return insertInQueueTask(taskBean,transactionFlag);
			} else {
				boolean removeFlag = removeLowestPriorityWorker(AsynTaskEnum.TaskPriotityType.FIRST);
				if (removeFlag) {
					return insertInQueueTask(taskBean,transactionFlag);
				} else {
					return insertPeddingTask(taskBean);
				}
			}
		} else {
			return insertPeddingTask(taskBean);
		}

	}

	/**
	 * 插入一条待处理任务
	 * @param taskBean
	 * @return
	 */
	private int insertPeddingTask(AsynTaskBean taskBean) {
		taskBean.setTimeoutLimit(new BigDecimal(asynTaskInfo.getTimeoutLimit()));
		if (taskBean.getDealNum() == null) {
			taskBean.setDealNum(new BigDecimal(1));
		}
		taskBean.setTaskType(asynTaskInfo.getTaskType());
		taskBean.setState(AsynTaskEnum.TaskStateType.PEDDING.getCode());
		return sqlSession.insert(asynTaskInfo.getMapper() + ".addAsynTask", taskBean);
	}


	/**
	 * 插一条任务进入队列
	 * @param taskBean
	 * @return
	 */
	private int insertInQueueTask(AsynTaskBean taskBean,boolean transactionFlag) {
		taskBean.setTimeoutLimit(new BigDecimal(asynTaskInfo.getTimeoutLimit()));
		if (taskBean.getDealNum() == null) {
			taskBean.setDealNum(new BigDecimal(1));
		}
		taskBean.setTaskType(asynTaskInfo.getTaskType());
		taskBean.setState(AsynTaskEnum.TaskStateType.IN_QUEUE.getCode());
		taskBean.setInQueueTime(new Date());
		taskBean.setIp(AsynTaskConstant.getIp());

		int ret = sqlSession.insert(asynTaskInfo.getMapper() + ".addAsynTask", taskBean);

		AsynTaskWorker dat = new AsynTaskWorker(taskBean, asynTaskInfo, this, new Date());
		if (!transactionFlag) {
			this.execute(dat);
		} else {
			AsynTaskThreadPoolManager.addPeddingWorker(dat);
		}
		return ret;
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
