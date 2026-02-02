package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
@Data
public class QualityTestInternalReport {

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @Schema(description = "成品代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productType;
    @Schema(description = "测试结果", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String result;
    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientName;
    @Schema(description = "订单数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderNum;
    @Schema(description = "测试开始时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testStartTime;
    @Schema(description = "测试结束时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testEndTime;
    private List<PduTestDataDetail> testData;



}
