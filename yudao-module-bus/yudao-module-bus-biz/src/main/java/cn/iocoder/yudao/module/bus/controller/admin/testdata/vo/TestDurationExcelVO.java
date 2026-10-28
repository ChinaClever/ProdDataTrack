package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ExcelIgnoreUnannotated
public class TestDurationExcelVO {

    @ExcelProperty("成品代码")
    private String productSn;

    @ExcelProperty("模块序列号")
    private String moduleSn;

    @ExcelProperty("测试项")
    private String testItem;

    @ExcelProperty("耗时(秒)")
    private BigDecimal durationSeconds;

    @ExcelProperty("耗时(时:分:秒)")
    private String durationText;

    @ExcelProperty("测试记录数")
    private Integer recordCount;
}