package cn.iocoder.yudao.module.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "成品代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;

    private String softVersion;

    private String languageSelect;
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;

    private String orderNum;
    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String devName;

    private String toolName;
    @Schema(description = "测试结果", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testResult;

    private String startTime;

    private String testStep;
    @Schema(description = "测试项目", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testItem;

    private String testCfg;
    @Schema(description = "测试要求", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testRequest;

    private String testProcess;

    private String endTime;

    private String allData;

}
