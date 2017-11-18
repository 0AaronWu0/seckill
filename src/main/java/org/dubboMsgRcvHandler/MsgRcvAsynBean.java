package org.dubboMsgRcvHandler;

import org.aaron.asyntask.AsynTaskBean;
import org.aaron.asyntask.AsynTaskErrInfo;

public class MsgRcvAsynBean extends AsynTaskBean {

    public AsynTaskErrInfo executeEvent() throws Exception {
        System.out.println(" 执行收报任务");
        return new AsynTaskErrInfo();
    }
}
