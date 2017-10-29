package org.aaron.asyntask;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StopWatch.TaskInfo;

/**
 * 异步任务表(TSK_ASYN_TASK)
 * 新建异步任务需继承此类，并试实现executeEvent()方法
 * @author 
 * @version 1.0.0 2017-10-27
 */
@Entity
@Table(name = "TSK_ASYN_TASK")
public abstract class AsynTaskBean {
    
    /** 任务序号 */
    private String taskId;
    
    /** 任务关键字 */
    private String keywords;
    
    /** 任务类型 */
    private String taskType;
    
    /** 状态，0:待处理，1：进入队列，2：开售工作，3：任务成功，4：任务失败，5：超时被杀，6：超时被抢，7：任务撤销 */
    private String state;
    
    /** 优先级 */
    private BigDecimal priority;
    
    /** 提交时间 */
    private Date applyTime;
    
    /** 进入队列时间 */
    private Date inQueueTime;
    
    /** 抢占IP */
    private String ip;
    
    /** 工作线程号 */
    private String threadId;
    
    /** 开始工作时间 */
    private Date startWorkTime;
    
    /** 结束工作时间 */
    private Date endWorkTime;
    
    /** 错误代码 */
    private String errCode;
    
    /** 错误信息 */
    private String errMsg;
    
    /** 当前处理次数 */
    private BigDecimal dealNum;
    
    /** 超时时间（秒） */
    private BigDecimal timeoutLimit;
    
    /** 计划执行时间，如果实时就为空 */
    private Date planTime;
    
    /** 关联字段1 */
    private String refCol1;
    
    /** 关联字段2 */
    private String refCol2;
    
    /** 关联字段3 */
    private String refCol3;
    
    /** 备注1 */
    private String info1;
    
    /** 备注2 */
    private String info2;
    
    /** 备注3 */
    private String info3;
    
    //表中没有改字段，用于重做时数据库中生成planTimeSeconds
    public BigDecimal planTimeSeconds;
    
    /**
     * 异步任务处理自己的事
     * @return
     * @throws Exception
     */
    public abstract AsynTaskErrInfo executeEvent() throws Exception;
    
    /**
     * 可以自定义重做方法
     * @param errInfo
     * @param asynTaskInfo
     * @return
     */
    public boolean addRedoTask(AsynTaskErrInfo errInfo, AsynTaskInfo taskInfo){
    	boolean redoFlag = isNeedRedo(errInfo,taskInfo.getRedoNum());
    	if(redoFlag){
    		//如果有重做间隔，就设置下次执行时间（如果为空，就马上执行）
    		int redoTimeSplit = taskInfo.getRedoTimeSplit();
    		this.planTime = null ;
    		if(redoTimeSplit > 0){
    			this.planTimeSeconds = new BigDecimal(redoTimeSplit);
    		}else{
    			this.planTimeSeconds = null ;
    		}
    		this.taskId = taskInfo.getTaskId(null);
    		this.dealNum = dealNum.add(new BigDecimal(1));
    		this.state = AsynTaskEnum.TaskStateType.PEDDING.getCode();
    		this.ip = null ;
    		this.inQueueTime = null ;
    		taskInfo.getSqlSession().insert(taskInfo.getMapper() + ".insertSelective", this);
    	}
		return redoFlag;
    }
    
    private boolean isNeedRedo(AsynTaskErrInfo errInfo, int redoNum) {
		if(!errInfo.isSuccFlag() && errInfo.isRedoFlag()){
			if(this.dealNum.intValue() < redoNum){
				return true;
			}
			return false;
		}
		return false;
	}

	public BigDecimal getPlanTimeSeconds() {
		return planTimeSeconds;
	}

	public void setPlanTimeSeconds(BigDecimal planTimeSeconds) {
		this.planTimeSeconds = planTimeSeconds;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public void setInQueueTime(Date inQueueTime) {
		this.inQueueTime = inQueueTime;
	}

	public void setStartWorkTime(Date startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public void setEndWorkTime(Date endWorkTime) {
		this.endWorkTime = endWorkTime;
	}

	/**
     * 获取任务序号
     * 
     * @return 任务序号
     */
    public String getTaskId() {
        return this.taskId;
    }
     
    /**
     * 设置任务序号
     * 
     * @param taskId
     *          任务序号
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    /**
     * 获取任务关键字
     * 
     * @return 任务关键字
     */
    public String getKeywords() {
        return this.keywords;
    }
     
    /**
     * 设置任务关键字
     * 
     * @param keywords
     *          任务关键字
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    /**
     * 获取任务类型
     * 
     * @return 任务类型
     */
    public String getTaskType() {
        return this.taskType;
    }
     
    /**
     * 设置任务类型
     * 
     * @param taskType
     *          任务类型
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    /**
     * 获取状态，0:待处理，1：进入队列，2：开售工作，3：任务成功，4：任务失败，5：超时被杀，6：超时被抢，7：任务撤销
     * 
     * @return 状态
     */
    public String getState() {
        return this.state;
    }
     
    /**
     * 设置状态，0:待处理，1：进入队列，2：开售工作，3：任务成功，4：任务失败，5：超时被杀，6：超时被抢，7：任务撤销
     * 
     * @param state
     *          状态，0:待处理，1：进入队列，2：开售工作，3：任务成功，4：任务失败，5：超时被杀，6：超时被抢，7：任务撤销
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * 获取优先级
     * 
     * @return 优先级
     */
    public BigDecimal getPriority() {
        return this.priority;
    }
     
    /**
     * 设置优先级
     * 
     * @param priority
     *          优先级
     */
    public void setPriority(BigDecimal priority) {
        this.priority = priority;
    }
    
    
    /**
     * 获取抢占IP
     * 
     * @return 抢占IP
     */
    public String getIp() {
        return this.ip;
    }
     
    /**
     * 设置抢占IP
     * 
     * @param ip
     *          抢占IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    /**
     * 获取工作线程号
     * 
     * @return 工作线程号
     */
    public String getThreadId() {
        return this.threadId;
    }
     
    /**
     * 设置工作线程号
     * 
     * @param threadId
     *          工作线程号
     */
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
    
    
    /**
     * 获取错误代码
     * 
     * @return 错误代码
     */
    public String getErrCode() {
        return this.errCode;
    }
     
    /**
     * 设置错误代码
     * 
     * @param errCode
     *          错误代码
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
    
    /**
     * 获取错误信息
     * 
     * @return 错误信息
     */
    public String getErrMsg() {
        return this.errMsg;
    }
     
    /**
     * 设置错误信息
     * 
     * @param errMsg
     *          错误信息
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    
    /**
     * 获取当前处理次数
     * 
     * @return 当前处理次数
     */
    public BigDecimal getDealNum() {
        return this.dealNum;
    }
     
    /**
     * 设置当前处理次数
     * 
     * @param dealNum
     *          当前处理次数
     */
    public void setDealNum(BigDecimal dealNum) {
        this.dealNum = dealNum;
    }
    
    /**
     * 获取超时时间（秒）
     * 
     * @return 超时时间（秒）
     */
    public BigDecimal getTimeoutLimit() {
        return this.timeoutLimit;
    }
     
    /**
     * 设置超时时间（秒）
     * 
     * @param timeoutLimit
     *          超时时间（秒）
     */
    public void setTimeoutLimit(BigDecimal timeoutLimit) {
        this.timeoutLimit = timeoutLimit;
    }
    
    /**
     * 获取计划执行时间，如果实时就为空
     * 
     * @return 计划执行时间
     */
    public Date getPlanTime() {
        return this.planTime;
    }
     
    /**
     * 设置计划执行时间，如果实时就为空
     * 
     * @param planTime
     *          计划执行时间，如果实时就为空
     */
    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }
    
    /**
     * 获取关联字段1
     * 
     * @return 关联字段1
     */
    public String getRefCol1() {
        return this.refCol1;
    }
     
    /**
     * 设置关联字段1
     * 
     * @param refCol1
     *          关联字段1
     */
    public void setRefCol1(String refCol1) {
        this.refCol1 = refCol1;
    }
    
    /**
     * 获取关联字段2
     * 
     * @return 关联字段2
     */
    public String getRefCol2() {
        return this.refCol2;
    }
     
    /**
     * 设置关联字段2
     * 
     * @param refCol2
     *          关联字段2
     */
    public void setRefCol2(String refCol2) {
        this.refCol2 = refCol2;
    }
    
    /**
     * 获取关联字段3
     * 
     * @return 关联字段3
     */
    public String getRefCol3() {
        return this.refCol3;
    }
     
    /**
     * 设置关联字段3
     * 
     * @param refCol3
     *          关联字段3
     */
    public void setRefCol3(String refCol3) {
        this.refCol3 = refCol3;
    }
    
    /**
     * 获取备注1
     * 
     * @return 备注1
     */
    public String getInfo1() {
        return this.info1;
    }
     
    /**
     * 设置备注1
     * 
     * @param info1
     *          备注1
     */
    public void setInfo1(String info1) {
        this.info1 = info1;
    }
    
    /**
     * 获取备注2
     * 
     * @return 备注2
     */
    public String getInfo2() {
        return this.info2;
    }
     
    /**
     * 设置备注2
     * 
     * @param info2
     *          备注2
     */
    public void setInfo2(String info2) {
        this.info2 = info2;
    }
    
    /**
     * 获取备注3
     * 
     * @return 备注3
     */
    public String getInfo3() {
        return this.info3;
    }
     
    /**
     * 设置备注3
     * 
     * @param info3
     *          备注3
     */
    public void setInfo3(String info3) {
        this.info3 = info3;
    }

	@Override
	public String toString() {
		return "AsynTaskBean [taskId=" + taskId + ", keywords=" + keywords + ", taskType=" + taskType + ", state="
				+ state + ", priority=" + priority + ", applyTime=" + applyTime + ", inQueueTime=" + inQueueTime
				+ ", ip=" + ip + ", threadId=" + threadId + ", startWorkTime=" + startWorkTime + ", endWorkTime="
				+ endWorkTime + ", errCode=" + errCode + ", errMsg=" + errMsg + ", dealNum=" + dealNum
				+ ", timeoutLimit=" + timeoutLimit + ", planTime=" + planTime + ", refCol1=" + refCol1 + ", refCol2="
				+ refCol2 + ", refCol3=" + refCol3 + ", info1=" + info1 + ", info2=" + info2 + ", info3=" + info3
				+ ", planTimeSeconds=" + planTimeSeconds + "]";
	}
    
    
}