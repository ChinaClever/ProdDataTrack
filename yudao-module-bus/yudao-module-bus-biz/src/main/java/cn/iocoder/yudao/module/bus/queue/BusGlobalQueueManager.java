package cn.iocoder.yudao.module.bus.queue;

import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.InformText;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class BusGlobalQueueManager {

    /**
     * 存储多个容量为1的有界对象队列
     */
    private final ConcurrentHashMap<String, ArrayBlockingQueue<InformText>> queues = new ConcurrentHashMap<>();

    /**
     * 初始化队列
     *
     * @param queueId 队列的唯一标识符
     */
    public void initializeQueue(String queueId) {
        queues.putIfAbsent(queueId, new ArrayBlockingQueue<>(1));
    }

    /**
     * 添加元素到指定队列，如果队列已满则替换旧的元素
     *
     * @param queueId 队列的唯一标识符
     * @param element 要添加的元素
     * @throws InterruptedException 如果被中断
     */
    public void addElementToQueue(String queueId, InformText element) throws InterruptedException {
        ArrayBlockingQueue<InformText> queue = queues.get(queueId);
        if (queue == null) {
            throw new IllegalArgumentException("队列 " + queueId + " 不存在");
        }
        // 如果队列已满，移除旧的元素
        if (!queue.offer(element)) {
            queue.poll();
            queue.put(element);
        }
    }

    /**
     * 获取所有队列的大小
     *
     * @return 所有队列的大小
     */
    public Map<String, Integer> getAllQueueSizes() {
        Map<String, Integer> sizes = new HashMap<>();
        for (Map.Entry<String, ArrayBlockingQueue<InformText>> entry : queues.entrySet()) {
            sizes.put(entry.getKey(), entry.getValue().size());
        }
        return sizes;
    }

    /**
     * 获取指定队列中的当前元素，不移除
     *
     * @param queueId 队列的唯一标识符
     * @return 队列中的元素
     */
    public InformText getElementFromQueue(String queueId) {
        ArrayBlockingQueue<InformText> queue = queues.get(queueId);
        if (queue == null) {
            throw new IllegalArgumentException("队列 " + queueId + " 不存在");
        }
        return queue.peek();
    }
}