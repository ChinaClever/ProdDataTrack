package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import lombok.Data;

@Data
public class ReportReqVO {
    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 成品代码
     */
    private Integer productSN;

    /**
     * 模块序列号
     */
    private String moduleSN;
}
