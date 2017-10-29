package org.aaron.asyntask;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AsynTaskEnum {
	
	public enum TaskPriotityType{
		
		FIRST(new BigDecimal(10)),HIGH(new BigDecimal(8)),NORMAL(new BigDecimal(6)),LOW(new BigDecimal(4)),
		LAST(new BigDecimal(1));
		
		private BigDecimal value;
		
		private TaskPriotityType(BigDecimal value){
			this.value = value;
		}
		
		public BigDecimal getValue(){
			return value;
		}
		
		public static TaskPriotityType getEnum(BigDecimal value){
			for (TaskPriotityType taskPriotityType : TaskPriotityType.values()) {
				if(taskPriotityType.value.compareTo(value) == 0 ){
					return taskPriotityType;
				}
			}
			return NORMAL;
		}
		
		public static List<TaskPriotityType> orderList = new ArrayList<TaskPriotityType>();
		
		static{
			orderList.add(TaskPriotityType.LAST);
			orderList.add(TaskPriotityType.LOW);
			orderList.add(TaskPriotityType.NORMAL);
			orderList.add(TaskPriotityType.HIGH);
			orderList.add(TaskPriotityType.FIRST);
		}
	}
	
	public enum TaskStateType{
		
		PEDDING("0","待处理"),IN_QUEUE("1","进入队列"),START_WORK("2","开始工作"),SUCC("3","任务成功"),FAIL("4",
				"任务失败"),TIME_OUT_KILL("5","超时被杀"),TIME_OUT_LOCK("6","超时被抢"),REMOVE("7","任务撤销");
		
		private String code;
		private String desc;
		
		private TaskStateType(String code,String desc){
			this.code = code;
			this.desc = desc;
		}
		
		public String getCode(){
			return code;
		}
		
		public String getDesc(){
			return desc;
		}
		
		public static TaskStateType getEnum(String code){
			for (TaskStateType taskStateType : TaskStateType.values()) {
				if(taskStateType.code.equals(code)){
					return taskStateType;
				}
			}
			return null;
		}
	}
	
}
