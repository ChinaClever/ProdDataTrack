package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module;

import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.PduTestDataDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PduModulesTestDataVo {

    private Long id;
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @Schema(description = "成品代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "模块类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleType;
    @Schema(description = "测试结果", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String result;
    @Schema(description = "mac地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String macAddress;
    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientName;
    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String companyName;
    @Schema(description = "软件版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String softVersion;
    @Schema(description = "语言选择 0-中文  1-英文 ", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String languageSelect;
    @Schema(description = "订单数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderNum;
    @Schema(description = "工具名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String toolName;
    @Schema(description = "测试开始时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testStartTime;
    @Schema(description = "测试结束时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testEndTime;
    @Schema(description = "协议版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String protocolVersion;
    @Schema(description = "工时", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testTime;
    @Schema(description = "测试日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testDate;
    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String specification;
    private List<PduTestDataDetail> testData;

}
