package org.aaron.asyntask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * 任务池管理类，用于事务存在时
 */
public class AsynTaskThreadPoolManager {
    //线程内缓存待执行任务
    private static ThreadLocal<ArrayList<AsynTaskWorker>> THREADLOCAL_TASK_WORKER = new ThreadLocal<ArrayList<AsynTaskWorker>>();
    //任务类型 任务池映
    private static Map<String,AsynTaskThreadPool> TYPE_POOL= new HashMap<String, AsynTaskThreadPool>();

    /**
     * 添加任务
     * @param taskType
     * @param threadPool
     */
    public static void addPool(String taskType,AsynTaskThreadPool threadPool){
        TYPE_POOL.put(taskType,threadPool);
    }

    /**
     * 联机任务添加到异步任务队列
     * @param commitFlag
     */
    public static void commitThreadLocalTask(boolean commitFlag){
        if (THREADLOCAL_TASK_WORKER.get() != null && THREADLOCAL_TASK_WORKER.get().size() != 0){
            if (commitFlag){
                for (AsynTaskWorker worker: THREADLOCAL_TASK_WORKER.get()) {
                    TYPE_POOL.get(worker.getAsynTaskInfo().getTaskType()).execute(worker);
                }
            }
            THREADLOCAL_TASK_WORKER.get().clear();
        }
    }

    /**
     * 清空线程本地化对象缓存，事务开启时调用
     */
    public static void clearThreadLocalTask(){
        if (THREADLOCAL_TASK_WORKER.get() != null && THREADLOCAL_TASK_WORKER.get().size() != 0){
            THREADLOCAL_TASK_WORKER.get().clear();
        }
    }

    /**
     * 插入一条待处理任务时，事务是开启的，
     * @param dat
     */
    public static void addPeddingWorker(AsynTaskWorker dat){
        if (THREADLOCAL_TASK_WORKER.get()==null){
            THREADLOCAL_TASK_WORKER.set(new ArrayList<AsynTaskWorker>());
        }
        THREADLOCAL_TASK_WORKER.get().add(dat);
    }

}
