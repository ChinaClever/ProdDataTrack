package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InformText {
    @Schema(description = "通知信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String message;
    @Schema(description = "时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testDate;
    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String title;

}
