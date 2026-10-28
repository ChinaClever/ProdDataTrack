package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import lombok.Data;

@Data
public class TestDurationReqVO {
    /**
     * 成品代码，必填
     */
    private String productSN;

    /**
     * 模块序列号，选填；为空时取成品代码下最新的模块序列号
     */
    private String moduleSN;
}