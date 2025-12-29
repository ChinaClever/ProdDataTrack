package cn.iocoder.yudao.module.system.controller.admin.clerk;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.dal.dataobject.clerk.FactoryReportClerk;
import cn.iocoder.yudao.module.system.service.clerk.ClerkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/factoryReportClerk")
@Tag(name = "管理后台 - 出厂报告审核")
public class FactoryReportClerkController {

    @Autowired
    private ClerkService clerkService;

    @GetMapping("/getFactoryReportClerk")
    @Operation(summary = "获取FactoryReportClerk")
    public CommonResult<FactoryReportClerk> getFactoryReportClerk() {
        CommonResult<FactoryReportClerk> commonResult = new CommonResult<>();
        FactoryReportClerk factoryReportClerk = clerkService.getFactoryReportClerk();
        return commonResult.setCode(200).setData(factoryReportClerk);
    }

    @PutMapping("/updateFactoryReportClerk")
    @Operation(summary = "更新FactoryReportClerk")
    public CommonResult<String> updateFactoryReportClerk(@RequestBody FactoryReportClerk factoryReportClerk) {
        clerkService.updateFactoryReportClerk(factoryReportClerk);
        return CommonResult.success("更新成功");
    }

}
