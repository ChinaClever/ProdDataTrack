package cn.iocoder.yudao.module.system.dal.dataobject.clerk;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@ToString
@TableName("factory_report_clerk")
@Data
public class FactoryReportClerk {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "检验员", requiredMode = Schema.RequiredMode.AUTO, example = "克莱沃")
    private String inspector;
    @Schema(description = "审核人", requiredMode = Schema.RequiredMode.AUTO, example = "克莱沃")
    private String auditor;
    @Schema(description = "批准人", requiredMode = Schema.RequiredMode.AUTO, example = "克莱沃")
    private String signer;

    @Schema(description = "检验员（英文）", requiredMode = Schema.RequiredMode.AUTO, example = "克莱沃")
    private String inspectorEnglish;
    @Schema(description = "审核人（英文）", requiredMode = Schema.RequiredMode.AUTO, example = "克莱沃")
    private String auditorEnglish;
    @Schema(description = "批准人（英文）", requiredMode = Schema.RequiredMode.AUTO, example = "克莱沃")
    private String signerEnglish;

    @Schema(description = "业务标识", requiredMode = Schema.RequiredMode.AUTO, example = "bus")
    private String identification;

}
