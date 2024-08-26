package cn.iocoder.yudao.module.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("test_data")
public class TestData {
    /**
     * 编号，数据库自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String productSn;

    private String moduleSn;

    private String softVersion;

    private String languageSelect;

    private String orderId;

    private String orderNum;

    private String devName;

    private String toolName;

    private String testResult;

    private String startTime;

    private String testStep;

    private String testItem;

    private String testCfg;

    private String testRequest;

    private String testProcess;

    private String endTime;

    private String allData;

}
