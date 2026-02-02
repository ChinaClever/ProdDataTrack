package cn.iocoder.yudao.module.system.controller.admin.mac;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.mac.vo.MacMaintainPageReqVo;
import cn.iocoder.yudao.module.system.controller.admin.mac.vo.MacMaintainReqVo;
import cn.iocoder.yudao.module.system.dal.dataobject.mac.MacMaintain;
import cn.iocoder.yudao.module.system.service.mac.MacMaintainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - mac地址管理")
@RestController
@RequestMapping("/system/mac")
@Validated
@Slf4j
public class MacMaintainController {

    @Autowired
    private MacMaintainService macMaintainService;

    private final Lock lock = new ReentrantLock();

    @GetMapping("/getAddress")
    @Operation(summary = "获取未分配mac地址")
    @PermitAll
    public ResponseEntity<?> getAddress(
            @RequestParam("orderId") String orderId,
            @RequestParam("productSn") String productSn,
            @RequestParam("moduleSn") String moduleSn) {

        MacMaintainReqVo macMaintainReqVo = new MacMaintainReqVo();
        macMaintainReqVo.setOrderId(orderId);
        macMaintainReqVo.setProductSn(productSn);
        macMaintainReqVo.setModuleSn(moduleSn);
        String macAddress;
        try {
            lock.lock();
            macAddress = macMaintainService.getAddress(macMaintainReqVo);
        } catch (Exception e) {
            log.error("获取MAC地址失败：", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取MAC地址失败: " + e.getMessage());
        } finally {
            lock.unlock();
        }
        return ResponseEntity.ok(macAddress);
    }

    @PostMapping("/getDistributionSituation")
    @Operation(summary = "获取mac地址分配情况")
    public CommonResult<PageResult<MacMaintain>> macAddressDistributionSituation(@RequestBody MacMaintainPageReqVo macMaintainPageReqVo) {
        PageResult<MacMaintain> macMaintains = macMaintainService.macAddressDistributionSituation(macMaintainPageReqVo);
        return success(macMaintains);
    }

}
