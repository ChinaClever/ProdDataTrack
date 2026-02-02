package cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PduTestDataDetail {
    @Schema(description = "序号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String no;
    @Schema(description = "测试步骤", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testStep;
    @Schema(description = "测试项", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testItem;
    @Schema(description = "测试要求", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testRequest;
    @Schema(description = "测试过程", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testProcess;
    @Schema(description = "测试结果 0-未通过  1-通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer testResult;

}
