package org.dubboMsgRcvHandler;

import org.aaron.asyntask.AsynTaskEnum;
import org.aaron.asyntask.AsynTaskThreadPool;
import org.dubbo.service.ReceiveService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class MsgReceiveServiceImpl implements ReceiveService{


    @Resource(name = "rcvMsgAsynThreadPool")
    AsynTaskThreadPool asynTaskThreadPool;

    public Map<String, Object> execute(Map<String, Object> map) {
        HashMap outMap = null ;
        map.get("xx");

        MsgRcvAsynBean msgRcvAsynBean = new MsgRcvAsynBean();
        msgRcvAsynBean.setKeywords("123123");
        msgRcvAsynBean.setPriority(AsynTaskEnum.TaskPriotityType.FIRST.getValue());
        msgRcvAsynBean.setRefCol1("123");

        asynTaskThreadPool.addAsynTask(msgRcvAsynBean,false);

        outMap.put("pro", "sdsd");

        return outMap;
    }


}
