package cn.iocoder.yudao.module.bus.service;

import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.InformText;
import cn.iocoder.yudao.module.bus.queue.BusGlobalQueueManager;
import org.springframework.stereotype.Service;

@Service
public class BusQueueService {

    private final BusGlobalQueueManager busGlobalQueueManager;

    public BusQueueService(BusGlobalQueueManager busGlobalQueueManager) {
        this.busGlobalQueueManager = busGlobalQueueManager;
    }

    /**
     * 添加元素到指定队列
     *
     * @param queueId 队列的唯一标识符
     * @param element 要添加的元素
     * @throws InterruptedException 如果被中断
     */
    public void addElementToQueue(String queueId, InformText element) throws InterruptedException {
        busGlobalQueueManager.addElementToQueue(queueId, element);
    }


    /**
     * 获取指定队列中的当前元素，不移除
     *
     * @param queueId 队列的唯一标识符
     * @return 队列中的元素
     */
    public InformText getElementFromQueue(String queueId) {
        return busGlobalQueueManager.getElementFromQueue(queueId);
    }
}
