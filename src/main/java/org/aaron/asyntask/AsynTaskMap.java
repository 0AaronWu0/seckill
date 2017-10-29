package org.aaron.asyntask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.aaron.asyntask.AsynTaskEnum.TaskPriotityType;

/**
 * 异步任务队列集合
 * @author as
 *
 */
public class AsynTaskMap {
	
	private	Map<TaskPriotityType,ArrayList<AsynTaskWorker>> taskQuqeueMap ;//按权限分类
	private	Map<String,AsynTaskWorker> taskIdWorkerMap ;//按taskID分类
	
	public AsynTaskMap(){
		taskQuqeueMap = new HashMap<TaskPriotityType, ArrayList<AsynTaskWorker>>();
		taskIdWorkerMap = new HashMap<String, AsynTaskWorker>();
		for(TaskPriotityType taskPriotityType :TaskPriotityType.values()){
			taskQuqeueMap.put(taskPriotityType ,new ArrayList<AsynTaskWorker>());
		}
	}
	
	public void clear(){
		taskIdWorkerMap.clear();
		for(TaskPriotityType taskPriotityType :TaskPriotityType.values()){
			taskQuqeueMap.get(taskPriotityType).clear();
		}
	}
	
	public void addWorker(AsynTaskWorker worker){
		taskQuqeueMap.get(TaskPriotityType.getEnum(worker.getAsynTaskBean().getPriority())).add(worker);
		taskIdWorkerMap.put(worker.getAsynTaskBean().getTaskId(),worker);
	}
	
	public void removeWorker(String taskId){
		removeWorker(getWorker(taskId));
	}
	
	public void removeWorker(AsynTaskWorker worker){
		taskQuqeueMap.get(TaskPriotityType.getEnum(worker.getAsynTaskBean().getPriority())).remove(worker);
		taskIdWorkerMap.remove(worker.getAsynTaskBean().getTaskId());
	}
	
	public AsynTaskWorker getWorker(String taskId){
		return taskIdWorkerMap.get(taskId);
	}
	
	public AsynTaskWorker getLowestWorker(){
		for(TaskPriotityType taskPriotityType:TaskPriotityType.orderList){
			if(taskQuqeueMap.get(taskPriotityType).size() != 0){
				ArrayList<AsynTaskWorker> workerList = taskQuqeueMap.get(taskPriotityType);
				return workerList.get(workerList.size()-1);
			}
		}
		return null;
	}
	
}
