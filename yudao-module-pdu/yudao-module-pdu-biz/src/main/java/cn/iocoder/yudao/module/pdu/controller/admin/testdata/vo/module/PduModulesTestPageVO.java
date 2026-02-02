package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PduModulesTestPageVO {
    private Integer pageNo;

    private Integer pageSize;
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @Schema(description = "成品序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "测试结果 0-未通过  1-通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String result;
    @Schema(description = "语言", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String language;
    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientName;
    @Schema(description = "模块类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleType;
    @Schema(description = "查询时间区间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String[] timeRange;
}
