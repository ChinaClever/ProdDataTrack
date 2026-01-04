package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TestDataPageReqVO{
    private Integer pageNo;

    private Integer pageSize;
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @Schema(description = "成品代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSN;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "测试项目", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testItem;
    @Schema(description = "测试要求", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testRequest;
    @Schema(description = "测试结果", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testResult;
    @Schema(description = "语言", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String language;

    private String[] timeRange;

}
