package cn.iocoder.yudao.module.pdu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("pdu_quality_test_data")
public class PduQualityTestData {
    /**
     * 编号，数据库自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderId;
    @Schema(description = "成品代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productSn;
    @Schema(description = "模块序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String moduleSn;
    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String productType;
    @Schema(description = "测试结果", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String result;
    @Schema(description = "mac地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String macAddress;
    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String clientName;
    @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String companyName;
    @Schema(description = "软件版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String softVersion;
    @Schema(description = "语言选择  0-中文  1-英文", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String languageSelect;
    @Schema(description = "订单数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String orderNum;
    @Schema(description = "工具名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String toolName;
    @Schema(description = "测试开始时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testStartTime;
    @Schema(description = "测试结束时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testEndTime;
    @Schema(description = "协议版本", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String protocolVersion;
    @Schema(description = "工时", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testTime;
    @Schema(description = "测试日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String testDate;
    @Schema(description = "规格", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String specification;
    @Schema(description = "执行板序列号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String outletSn;
    private String testData;


}
