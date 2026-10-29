package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ExcelIgnoreUnannotated
public class TestDurationExcelVO {

    @Schema(description = "成品代码", example = "1")
    @ExcelProperty("成品代码")
    private String productSn;

    @Schema(description = "模块序列号", example = "1")
    @ExcelProperty("模块序列号")
    private String moduleSn;

    @Schema(description = "测试步骤", example = "安规测试")
    @ExcelProperty("测试步骤")
    private String testStep;

    @Schema(description = "耗时（秒）", example = "10.5")
    @ExcelProperty("耗时(秒)")
    private BigDecimal durationSeconds;

    @Schema(description = "耗时（时:分:秒）", example = "00:00:10")
    @ExcelProperty("耗时(时:分:秒)")
    private String durationText;
}