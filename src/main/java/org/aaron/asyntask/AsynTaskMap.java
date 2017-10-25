package org.aaron.asyntask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 异步任务队列集合
 * @author as
 *
 */
public class AsynTaskMap {
	
	private	Map<String,ArrayList<AsynTaskWorker>> taskQuqeueMap ;//按权限分类
	private	Map<String,AsynTaskWorker> taskIdWorkerMap ;//按taskID分类
	
	public AsynTaskMap(){
		taskQuqeueMap = new HashMap<String, ArrayList<AsynTaskWorker>>();
		taskIdWorkerMap = new HashMap<String, AsynTaskWorker>();
		//TODO
	}
	
	public void clear(){
		taskIdWorkerMap.clear();
	}
	
	public void addWorker(AsynTaskWorker worker){
		//TODO
	}
	
	public void removeWorker(String taskId){
		removeWorker(getWorker(taskId));
		//TODO
	}
	
	public void removeWorker(AsynTaskWorker worker){
		//TODO
	}
	
	public AsynTaskWorker getWorker(String taskId){
		return taskIdWorkerMap.get(taskId);
	}
	
	public Thread getLowestWorker(){
		//TODO
		return null;
	}
	
}
