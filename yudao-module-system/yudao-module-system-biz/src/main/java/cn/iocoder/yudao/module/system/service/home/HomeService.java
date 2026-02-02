package cn.iocoder.yudao.module.system.service.home;

import cn.iocoder.yudao.module.system.controller.admin.home.vo.ShipmentNumVo;

import java.util.List;

public interface HomeService {
    List<ShipmentNumVo> getShipmentNum();
}
