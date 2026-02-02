package cn.iocoder.yudao.module.system.controller.admin.home.vo;

import lombok.Data;

@Data
public class ShipmentNumVo {

    private String name;

    private Integer month;

    private Integer year;

    private Integer lastYear;

    public ShipmentNumVo(String name) {
        this.name = name;
        this.month = 0;
        this.year = 0;
        this.lastYear = 0;
    }

    // 构造方法
    public ShipmentNumVo() {
        this.month = 0;
        this.year = 0;
        this.lastYear = 0;
    }
}
