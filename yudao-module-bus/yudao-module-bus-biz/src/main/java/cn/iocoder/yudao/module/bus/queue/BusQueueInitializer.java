package cn.iocoder.yudao.module.bus.queue;

import cn.hutool.core.date.DateTime;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.InformText;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BusQueueInitializer implements CommandLineRunner {

    private final BusGlobalQueueManager busGlobalQueueManager;

    public BusQueueInitializer(BusGlobalQueueManager busGlobalQueueManager) {
        this.busGlobalQueueManager = busGlobalQueueManager;
    }

    @Override
    public void run(String... args) throws Exception {
        // 初始化多个队列，添加一些初始元素
        busGlobalQueueManager.initializeQueue("busModule");
        busGlobalQueueManager.initializeQueue("busQuality");
        InformText module = new InformText();
        DateTime dateTime = new DateTime();
        String timeString = dateTime.toString("yyyy-MM-dd HH:mm:ss");
        module.setMessage("初始化消息");
        module.setTestDate(timeString);
        module.setTitle("母线校准");

        InformText quality = new InformText();
        quality.setMessage("初始化消息");
        quality.setTestDate(timeString);
        quality.setTitle("母线质检");

        busGlobalQueueManager.addElementToQueue("busModule", module);
        busGlobalQueueManager.addElementToQueue("busQuality", quality);
        System.out.println("队列初始化完成，当前队列大小: " + busGlobalQueueManager.getAllQueueSizes());
    }
}
