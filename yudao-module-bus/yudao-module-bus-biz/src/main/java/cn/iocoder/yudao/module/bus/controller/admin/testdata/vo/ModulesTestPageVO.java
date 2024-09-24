package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import lombok.Data;

@Data
public class ModulesTestPageVO {
    private Integer pageNo;

    private Integer pageSize;

    private String orderId;

    private String productSN;

    private String testResult;

    private String language;

    private String[] timeRange;
}
