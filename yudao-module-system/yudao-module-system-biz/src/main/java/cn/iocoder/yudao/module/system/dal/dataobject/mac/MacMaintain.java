package cn.iocoder.yudao.module.system.dal.dataobject.mac;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("mac_maintain")
public class MacMaintain {
    /**
     * 编号，数据库自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientName;
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @Schema(description = "成品序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "mac地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String macAddress;


}
