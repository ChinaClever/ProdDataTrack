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

    @Schema(description = "测试项", example = "交流耐压测试")
    @ExcelProperty("测试项")
    private String testName;

    @Schema(description = "开始时间", example = "2024-09-27 17:17:08")
    @ExcelProperty("开始时间")
    private String startTime;

    @Schema(description = "结束时间", example = "2024-09-27 17:18:38")
    @ExcelProperty("结束时间")
    private String endTime;

    @Schema(description = "耗时（秒）", example = "10.5")
    @ExcelProperty("耗时(秒)")
    private BigDecimal durationSeconds;

    @Schema(description = "耗时（时:分:秒）", example = "00:00:10")
    @ExcelProperty("耗时(时:分:秒)")
    private String durationText;
}