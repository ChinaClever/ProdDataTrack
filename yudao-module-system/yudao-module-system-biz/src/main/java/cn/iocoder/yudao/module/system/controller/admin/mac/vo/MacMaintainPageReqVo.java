package cn.iocoder.yudao.module.system.controller.admin.mac.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MacMaintainPageReqVo {
    private Integer pageNo;

    private Integer pageSize;
    @NotBlank(message = "客户名称不能为空")
    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientName;
    @NotBlank(message = "订单号不能为空")
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @NotBlank(message = "成品序列号不能为空")
    @Schema(description = "成品序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @NotBlank(message = "模块序列号不能为空")
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "mac地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String macAddress;
}
