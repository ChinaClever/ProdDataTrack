package cn.iocoder.yudao.module.bus.controller.admin.testdata.vo;

import lombok.Data;

@Data
public class ReportRespVO {
    /**
     * 产品名称
     */
    private String devName;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 规格型号
     */
    private String deviceType;

    /**
     * 生产日期
     */
    private String productionDate;

    /**
     * 检验日期
     */
    private String testDate;

    /**
     * 生产数量
     */
    private Integer productionNum;

    /**
     * 检测数量
     */
    private Integer passTestNum;


}
