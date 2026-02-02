package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PduReportReqVO {
    /**
     * 订单号
     */
    private String orderId;

    /**
     * 成品代码
     */
    private String productSN;

    /**
     * 模块序列号
     */
    @Schema(description = "外部报告时不传", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSN;
}
