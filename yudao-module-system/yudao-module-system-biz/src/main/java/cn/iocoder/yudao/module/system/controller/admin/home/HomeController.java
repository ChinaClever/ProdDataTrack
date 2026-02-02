package cn.iocoder.yudao.module.system.controller.admin.home;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.home.vo.ShipmentNumVo;
import cn.iocoder.yudao.module.system.service.home.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 首页")
@RestController
@RequestMapping("/system/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/shipmentNum")
    @Operation(summary = "获取各产品出货数量")
    public CommonResult<List<ShipmentNumVo>> getShipmentNum(){
        List<ShipmentNumVo> shipmentNumVos = homeService.getShipmentNum();
        return success(shipmentNumVos);
    }

}
