package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TestDurationReqVO {

    @Schema(description = "成品代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSN;

    @Schema(description = "模块序列号，为空时取成品代码下最新的模块序列号", example = "1")
    private String moduleSN;
}