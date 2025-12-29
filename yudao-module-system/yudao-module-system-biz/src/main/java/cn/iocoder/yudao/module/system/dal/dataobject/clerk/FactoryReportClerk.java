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

    @Schema(description = "检验员", requiredMode = Schema.RequiredMode.REQUIRED, example = "克莱沃")
    private String inspector;
    @Schema(description = "审核人", requiredMode = Schema.RequiredMode.REQUIRED, example = "克莱沃")
    private String auditor;
    @Schema(description = "批准人", requiredMode = Schema.RequiredMode.REQUIRED, example = "克莱沃")
    private String signer;

}
