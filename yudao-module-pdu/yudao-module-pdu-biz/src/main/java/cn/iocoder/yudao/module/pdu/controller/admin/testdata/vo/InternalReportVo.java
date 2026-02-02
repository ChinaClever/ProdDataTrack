package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InternalReportVo {
    @Schema(description = "成品质检内部报告", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private QualityTestInternalReport qualityTestInternalReport;
    @Schema(description = "半成品调试内部报告（模块）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private ModuleTestInternalReport moduleTestInternalReport;

}
