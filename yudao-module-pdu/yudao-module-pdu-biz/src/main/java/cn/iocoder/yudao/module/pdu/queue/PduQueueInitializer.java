package cn.iocoder.yudao.module.pdu.queue;

import cn.hutool.core.date.DateTime;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.InformText;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PduQueueInitializer implements CommandLineRunner {

    private final PduGlobalQueueManager pduGlobalQueueManager;

    public PduQueueInitializer(PduGlobalQueueManager pduGlobalQueueManager) {
        this.pduGlobalQueueManager = pduGlobalQueueManager;
    }

    @Override
    public void run(String... args) throws Exception {
        // 初始化多个队列，添加一些初始元素
        pduGlobalQueueManager.initializeQueue("pduModule");
        pduGlobalQueueManager.initializeQueue("pduQuality");
        InformText module = new InformText();
        DateTime dateTime = new DateTime();
        String timeString = dateTime.toString("yyyy-MM-dd HH:mm:ss");
        module.setMessage("初始化消息");
        module.setTestDate(timeString);
        module.setTitle("PDU校准");

        InformText quality = new InformText();
        quality.setMessage("初始化消息");
        quality.setTestDate(timeString);
        quality.setTitle("PDU质检");
        pduGlobalQueueManager.addElementToQueue("pduModule", module);
        pduGlobalQueueManager.addElementToQueue("pduQuality", quality);
        System.out.println("队列初始化完成，当前队列大小: " + pduGlobalQueueManager.getAllQueueSizes());
    }
}
