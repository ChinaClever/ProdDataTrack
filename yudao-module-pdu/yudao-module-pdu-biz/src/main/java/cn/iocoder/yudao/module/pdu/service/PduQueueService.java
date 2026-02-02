package cn.iocoder.yudao.module.pdu.service;

import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.InformText;
import cn.iocoder.yudao.module.pdu.queue.PduGlobalQueueManager;
import org.springframework.stereotype.Service;

@Service
public class PduQueueService {

    private final PduGlobalQueueManager pduGlobalQueueManager;

    public PduQueueService(PduGlobalQueueManager pduGlobalQueueManager) {
        this.pduGlobalQueueManager = pduGlobalQueueManager;
    }

    /**
     * 添加元素到指定队列
     *
     * @param queueId 队列的唯一标识符
     * @param element 要添加的元素
     * @throws InterruptedException 如果被中断
     */
    public void addElementToQueue(String queueId, InformText element) throws InterruptedException {
        pduGlobalQueueManager.addElementToQueue(queueId, element);
    }


    /**
     * 获取指定队列中的当前元素，不移除
     *
     * @param queueId 队列的唯一标识符
     * @return 队列中的元素
     */
    public InformText getElementFromQueue(String queueId) {
        return pduGlobalQueueManager.getElementFromQueue(queueId);
    }
}
