package org.aaron.asyntask;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aaron.monitor.ThreadMonitor;
import org.aaron.monitor.ThreadStatus;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步任务获取线程
 * @author as
 *
 */
public class AsynTaskGetTaskThread extends AbstractAsynTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public SqlSessionTemplate sqlSession;
	private volatile boolean running ;//运行标志
	private Thread getTaskThread = null;//当前线程对象
	private AsynTaskThreadPool asynTaskThreadPool;//线程池对象
	private AsynTaskInfo asynTaskInfo;//线程池配置信息对象
	private ThreadStatus stauts;
	
	@Override
	public ThreadStatus getStatus() {
		return this.stauts;
	}
	
	/**
	 * 初始化执行方法
	 */
	public void initialize(){
		logger.info("获取任务线程开始: "+asynTaskInfo.toString() +" ； " + asynTaskThreadPool.toString());
		try{
			this.running = true;
			this.getTaskThread = new Thread(this);
			this.getTaskThread.setName(asynTaskInfo.getTaskThreadName());
			this.getTaskThread.start();
			//创建当前线程状态对象，传入监控线程
			stauts = new ThreadStatus();
			stauts.setThreadName(asynTaskInfo.getTaskThreadName());
			stauts.setTaskType(asynTaskInfo.getTaskType());
			stauts.setLastReportTime(new Date());
			stauts.setThreadPoolIp(AsynTaskConstant.getIp());
			ThreadMonitor.getInstance().addMonitoredThread(asynTaskInfo.getTaskThreadName(),this);
			logger.info("获取任务线程启动成功");
		}catch (Exception e) {
			logger.error("获取任务线程异常: ",e);
		}
	}
	
	public void run() {
		try{
			Thread.sleep(AsynTaskConstant.TASK_THREAD_START_SLEEP_TIME);
		}catch (InterruptedException e) {
			logger.error("获取任务线程启动后，休息异常: ",e);
		}
		//获取抢任务条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ip", AsynTaskConstant.getIp());
		map.put("taskNum", asynTaskInfo.getTaskNnum());
		map.put("taskType", asynTaskInfo.getTaskType());
		//TODO
//		asynTaskInfo.setParamMap(map);
		int updTaskCount = 0;
		//获取对应异步任务表的Mapper.xml 
		String asynTaskMapper = asynTaskInfo.getMapper();
		String updateTaskMapperName = asynTaskInfo.getUpdateTaskMapperName(); 
		String getTaskListMapperName = asynTaskInfo.getTaskListMapperName(); 
		try{
			while(running){
				//每次批量处理异步任务的开始时间，存入线程状态，用于线程监控
				stauts.setLastReportTime(new Date());
				
				//如果线程池没有超过阈值，就去获取任务数据
				if(!asynTaskThreadPool.isBusy()){
					//抢任务
					try{
						updTaskCount = sqlSession.update(updateTaskMapperName, map);
					}catch (Exception e) {
						logger.error(asynTaskInfo.getTaskThreadName() + "抢任务异常: ",e);
					}
					//抢到任务
					if(updTaskCount> 0){
						List<AsynTaskBean> taskList = null;
						//获取异步任务
						try{
							taskList = sqlSession.selectList(getTaskListMapperName, map);
						}catch (Exception e) {
							logger.error(asynTaskInfo.getTaskThreadName() +"抢任务,后获取任务异常: ",e);
						}
						//更新异步任务状态为进入队列
						try{
							sqlSession.update(asynTaskMapper+".updateStateAfterGetTask", map);
						}catch (Exception e) {
							logger.error(asynTaskInfo.getTaskThreadName() +"抢任务,获取任务,后更新异步任务表状态异常: ",e);
						}
						//将异步任务放入队列中
						for (AsynTaskBean asynTaskBean : taskList) {
							AsynTaskWorker dat = new AsynTaskWorker(asynTaskBean, asynTaskInfo, asynTaskThreadPool, 
									new Date());
							asynTaskThreadPool.execute(dat);
						}
						
					}else{
						//如果没有抢到任务休息一会
						Thread.sleep(asynTaskInfo.getTaskSleepTime());
					}
				}else{
					logger.info(asynTaskInfo.getTaskThreadName() +" :线程池繁忙 ");
				}
			}
		}catch (Exception e) {
			logger.error("抢任务线程运行异常: ",e);
		}
	}
	
	public void terminate(){
		logger.info(asynTaskInfo.getTaskThreadName() +" :线程销毁开始 ");
		running = false ;
		try{
			if(null != this.getTaskThread){
				this.getTaskThread.interrupt();
			}
			logger.info(asynTaskInfo.getTaskThreadName() +" :线程销毁成功 ");
		}catch (Exception e) {
			logger.error(asynTaskInfo.getTaskThreadName() + "线程销毁异常: ",e);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public AsynTaskThreadPool getAsynTaskThreadPool() {
		return asynTaskThreadPool;
	}

	public void setAsynTaskThreadPool(AsynTaskThreadPool asynTaskThreadPool) {
		this.asynTaskThreadPool = asynTaskThreadPool;
	}

	public AsynTaskInfo getAsynTaskInfo() {
		return asynTaskInfo;
	}

	public void setAsynTaskInfo(AsynTaskInfo asynTaskInfo) {
		this.asynTaskInfo = asynTaskInfo;
	}

	

}
