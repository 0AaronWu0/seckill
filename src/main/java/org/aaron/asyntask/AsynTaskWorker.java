package org.aaron.asyntask;

import java.util.Date;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsynTaskWorker implements Runnable, Comparable<AsynTaskWorker> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SqlSessionTemplate sqlSession;//注入常量类中的sqlSession
	private int priority = 1;
	private AsynTaskBean asynTaskBean;//线程池任务对象
	private AsynTaskInfo asynTaskInfo;
	private AsynTaskThreadPool asynTaskServer;//线程池对象
	
	public AsynTaskWorker(AsynTaskBean asynTaskBean,AsynTaskInfo asynTaskInfo,AsynTaskThreadPool asynTaskServer,Date date){
		this.priority = asynTaskBean.getPriority().intValue()*1000000 + getMillsNum(date);
		this.asynTaskBean = asynTaskBean;
		this.asynTaskInfo = asynTaskInfo;
		this.asynTaskServer = asynTaskServer;
		this.sqlSession = AsynTaskConstant.getSqlSession;
	}
	
	public void run() {
		//获取Mapper.xml命名空间
		String mapper = asynTaskInfo.getMapper();
		//开始工作后移除队列Map中的数据
		asynTaskServer.removeMap(asynTaskBean.getTaskId());
		
		String threadId = Thread.currentThread().getName();
		asynTaskServer.addThreadMap(threadId, Thread.currentThread());
		try{
			asynTaskBean.setThreadId(threadId);
			logger.info("更新任务状态开始" + System.currentTimeMillis());
			sqlSession.update(mapper+".updateStateToStart",asynTaskBean);
			logger.info("更新任务状态结束" + System.currentTimeMillis());
		}catch (Exception e) {
			logger.error("更新任务状态异常" + asynTaskBean,e);
			return ;
		}
		//异步任务对象执行自己的executeEvent方法，返回一个AsynTaskErrInfo，来判断是否重做
		AsynTaskErrInfo errInfo;
		try{
			errInfo = asynTaskBean.executeEvent();
		}catch (Exception e) {
			String msg = asynTaskInfo.getServerName()+"[" + threadId + "]内部线程异常" + e.getMessage(); 
			logger.error(msg ,e);
			errInfo = new AsynTaskErrInfo(false,false,"C10100001",msg,e);
		}
		
		//结束时把任务状态改为成功/失败
		logger.info("处理完成，更新任务状态开始" + System.currentTimeMillis());
		try{
			if(errInfo.isSuccFlag()){
				sqlSession.update(mapper+".updateStateToSuccess",asynTaskBean);
			}else{
				String cause= "";
				if(null != errInfo.getT()){
					cause = errInfo.getT().toString();
				}
				logger.error(errInfo.getErrCode() +" " + errInfo.getErrMsg(),errInfo.getT());
				asynTaskBean.setErrCode(errInfo.getErrCode());
				asynTaskBean.setErrMsg(errInfo.getErrMsg());
				sqlSession.update(mapper+".updateStateToFail",asynTaskBean);
			}
		}catch (Exception e) {
			logger.error("处理完成，更新任务状态异常" + asynTaskBean,e);
			return ;
		}
		logger.info("处理完成，更新任务状态结束" + System.currentTimeMillis());
		
		try{
			asynTaskBean.addRedoTask(errInfo, mapper);
		}catch (Exception e) {
			logger.error("添加重做任务异常" ,e);
		}
		logger.info("任务结束" + System.currentTimeMillis());
	}

	public int compareTo(AsynTaskWorker o) {
		if(priority > o.priority){
			return -1;
		}else{
			if(priority < o.priority){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
	@Override
	public boolean equals(Object o){
		return this == o;
	}
	
	public int getMillsNum(Date date){
		long time = date.getTime();
		int num = (int)(time * 1000000);
		return 1000000 - num ;
	}

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public AsynTaskBean getAsynTaskBean() {
		return asynTaskBean;
	}

	public void setAsynTaskBean(AsynTaskBean asynTaskBean) {
		this.asynTaskBean = asynTaskBean;
	}

	public AsynTaskInfo getAsynTaskInfo() {
		return asynTaskInfo;
	}

	public void setAsynTaskInfo(AsynTaskInfo asynTaskInfo) {
		this.asynTaskInfo = asynTaskInfo;
	}

	public AsynTaskThreadPool getAsynTaskServer() {
		return asynTaskServer;
	}

	public void setAsynTaskServer(AsynTaskThreadPool asynTaskServer) {
		this.asynTaskServer = asynTaskServer;
	}
}
