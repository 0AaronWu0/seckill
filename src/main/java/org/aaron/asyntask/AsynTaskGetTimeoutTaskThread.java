package org.aaron.asyntask;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.aaron.asyntask.AsynTaskEnum.TaskStateType;
import org.aaron.monitor.ThreadMonitor;
import org.aaron.monitor.ThreadStatus;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 超时异步任务获取线程
 * @author as
 *
 */
public class AsynTaskGetTimeoutTaskThread extends AbstractAsynTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	public SqlSessionTemplate sqlSession;
	private volatile boolean running = true ;//运行标志
	private Thread getTimeoutTaskThread = null;//当前线程对象
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
			this.getTimeoutTaskThread = new Thread(this);
			this.getTimeoutTaskThread.setName(asynTaskInfo.getTaskThreadName());
			this.getTimeoutTaskThread.start();
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
			Thread.sleep(AsynTaskConstant.TIMEOUT_THREAD_START_SLEEP_TIME);
		}catch (InterruptedException e) {
			logger.error("获取任务线程启动后，休息异常: ",e);
		}
		//获取抢任务条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ip", AsynTaskConstant.getIp());
		map.put("taskType", asynTaskInfo.getTaskType());

		int updTaskCount = 0;
		//获取对应异步任务表的Mapper.xml 
		String asynTaskMapper = asynTaskInfo.getMapper();
		String errCode = "CI00001";
		String errMsg = "超时终止";
		AsynTaskErrInfo errInfo = new AsynTaskErrInfo(false, true, errCode, errMsg, null);
		try{
			while(running){
				//每次批量处理异步任务的开始时间，存入线程状态，用于线程监控
				stauts.setLastReportTime(new Date());
				
				//抢任务
				try{
					updTaskCount = sqlSession.update(asynTaskMapper + "updateStateAndIpWhenDoubleTimeOut", map);
				}catch (Exception e) {
					logger.error(asynTaskInfo.getTaskThreadName() + "抢任务异常: ",e);
				}
				//抢到任务
				if(updTaskCount> 0){
					List<AsynTaskBean> taskList = null;
					//获取异步任务
					try{
						taskList = sqlSession.selectList(asynTaskMapper + "getMyIpTimeoutTaskList", map);
					}catch (Exception e) {
						logger.error(asynTaskInfo.getTaskThreadName() +"抢任务,后获取任务异常: ",e);
					}
					try{
						//将异步任务放入队列中
						for (AsynTaskBean asynTaskBean : taskList) {
							if(TaskStateType.START_WORK == TaskStateType.getEnum(asynTaskBean.getState())
									&& AsynTaskConstant.getIp().equals(asynTaskBean.getIp())){
								Thread worker = asynTaskThreadPool.getThread(asynTaskBean.getThreadId());
								if(null != worker){
									try{
										worker.interrupt();
									}catch (Exception e) {
										logger.error("中断超时线程异常 " + asynTaskInfo.getTaskThreadName(),e);
										continue;
									}
								}
							}
							if(TaskStateType.IN_QUEUE == TaskStateType.getEnum(asynTaskBean.getState())
									&& AsynTaskConstant.getIp().equals(asynTaskBean.getIp())){
								if(!asynTaskThreadPool.removeQueueAndMap(asynTaskBean.getTaskId())){
									continue;
								}
							}
							asynTaskBean.setErrCode(errCode);
							asynTaskBean.setErrMsg(errMsg);
							
							try{
								sqlSession.update(asynTaskMapper+".updateStateToKill", asynTaskBean);
							}catch (Exception e) {
								logger.error(asynTaskInfo.getTaskThreadName() +"更新任务状态超时被杀异常: ",e);
								continue;
							}
							
							try{
								asynTaskBean.addRedoTask(errInfo, asynTaskInfo);
							}catch (Exception e) {
								logger.error(asynTaskInfo.getTaskThreadName() +"添加重做任务异常: ",e);
							}
							
						}
						sqlSession.update(asynTaskMapper+".updateStateAfterGetTask", map);
					}catch (Exception e) {
						logger.error(asynTaskInfo.getTaskThreadName() +"抢任务,获取任务,后更新异步任务表状态异常: ",e);
					}
					
				}else{
					//如果没有抢到任务休息一会
					Thread.sleep(asynTaskInfo.getTimeoutTaskSleepTime());
				}
			}
		}catch (Exception e) {
			logger.error("抢任务线程运行异常: ",e);
		}
	}
	
	//销毁方法
	public void terminate(){
		logger.info(asynTaskInfo.getTaskThreadName() +" :线程销毁开始 ");
		running = false ;
		try{
			if(null != this.getTimeoutTaskThread){
				this.getTimeoutTaskThread.interrupt();
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
