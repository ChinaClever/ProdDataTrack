package cn.iocoder.yudao.module.pdu.controller.admin.testdata;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.*;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module.PduModulesTestDataVo;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.module.PduModulesTestPageVO;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.quality.PduQualityTestDataVo;
import cn.iocoder.yudao.module.pdu.controller.admin.testdata.vo.quality.PduTestDataPageReqVO;
import cn.iocoder.yudao.module.pdu.entity.PduModulesTestData;
import cn.iocoder.yudao.module.pdu.entity.PduQualityTestData;
import cn.iocoder.yudao.module.pdu.service.PduModulesTestService;
import cn.iocoder.yudao.module.pdu.service.PduQualityTestDataService;
import cn.iocoder.yudao.module.pdu.service.PduQueueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.lang.Console.log;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Slf4j
@RestController
@RequestMapping("/pdu")
@Tag(name = "Pdu - 质检/调试")
public class PduController {

    @Autowired
    private PduQualityTestDataService pduQualityTestDataService;

    @Autowired
    private PduModulesTestService pduModulesTestService;

    @Autowired
    private PduQueueService pduQueueService;

    @PostMapping("/pduTestData")
    @Operation(summary = "存储pdu测试数据")
    @PermitAll
    public ResponseEntity<?> receiveTestData(@RequestBody PduQualityTestDataVo pduQualityTestDataVo) {
        try {
            pduQualityTestDataService.savePduQualityTestData(pduQualityTestDataVo);
        } catch (Exception e) {
            log("存储测试数据失败：" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("存储测试数据失败" + e.getMessage());
            // 如果保存失败，返回500状态码和错误信息
        }
        return ResponseEntity.ok("存储测试数据成功");
    }

    @Operation(summary = "获取pdu测试数据")
    @GetMapping("/testData-page")
    public CommonResult<PageResult<PduQualityTestDataVo>> getTestDataPage(PduTestDataPageReqVO pageReqVO) {
        PageResult<PduQualityTestDataVo> pageResult = pduQualityTestDataService.getTestDataPage(pageReqVO);
        return success(pageResult);
    }

    @PutMapping("/updateQualityTestData")
    @Operation(summary = "更新pdu成品质检数据")
    public CommonResult<String> updateTestData(@RequestBody PduQualityTestDataVo pduQualityTestDataVo) {
        PduQualityTestData pduQualityTestData = new PduQualityTestData();
        BeanUtils.copyProperties(pduQualityTestDataVo, pduQualityTestData);
        pduQualityTestData.setTestData(pduQualityTestDataVo.getTestData().toString());
        pduQualityTestDataService.updateTestData(pduQualityTestData);
        return success("更新成功");
    }

    @DeleteMapping("/deleteTestData")
    @Operation(summary = "删除成品质检数据")
    public CommonResult<String> deleteTestData(@RequestParam("id") Integer id) {
        pduQualityTestDataService.deleteTestData(id);
        return success("删除成功");
    }

    @PostMapping("/report")
    @Operation(summary = "pdu成品质检外部报告")
    @PermitAll
    public CommonResult<PduReportRespVO> getReportInfo(@RequestBody PduReportReqVO reqVO) {
        PduReportRespVO respVO = pduQualityTestDataService.getReportInfo(reqVO);
        return success(respVO);

    }

    @GetMapping("/internal-report")
    @Operation(summary = "pud成品质检内部报告")
    public CommonResult<InternalReportVo> getInternalReport(PduReportReqVO reqVO) {
        InternalReportVo pageResult = pduQualityTestDataService.getInternalReport(reqVO);
        return success(pageResult);
    }

    @PostMapping("/moduleTestData")
    @Operation(summary = "存储模块质检数据")
    @PermitAll
    public ResponseEntity<?> receiveModulesTest(@RequestBody PduModulesTestDataVo pduModulesTestDataVo) {
        try {
            pduModulesTestService.savePduModulesTestData(pduModulesTestDataVo);
        } catch (Exception e) {
            log("存储测试数据失败：" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("存储测试数据失败" + e.getMessage());
            // 如果保存失败，返回500状态码和错误信息
        }
        return ResponseEntity.ok("存储测试数据成功");
    }

    @GetMapping("/moduleTest-page")
    @Operation(summary = "模块质检数据查询")
    public CommonResult<PageResult<PduModulesTestDataVo>> getModulesTestPa(PduModulesTestPageVO pageReqVO) {
        PageResult<PduModulesTestDataVo> pageResult = pduModulesTestService.getModulesTestPage(pageReqVO);
        return success(pageResult);
    }

    @PutMapping("/updateModuleTest")
    @Operation(summary = "更新模块质检数据")
    public CommonResult<String> updateModuleTest(@RequestBody PduModulesTestDataVo pduModulesTestDataVo) {
        PduModulesTestData pduModulesTestData = new PduModulesTestData();
        BeanUtils.copyProperties(pduModulesTestDataVo,pduModulesTestData);
        pduModulesTestData.setTestData(pduModulesTestDataVo.getTestData().toString());
        pduModulesTestService.updateModuleTest(pduModulesTestData);
        return success("更新成功");
    }

    @DeleteMapping("/deleteModuleTest")
    @Operation(summary = "删除模块质检数据")
    public CommonResult<String> deleteModuleTest(@RequestParam("id") Integer id) {
        pduModulesTestService.deleteModuleTest(id);
        return success("删除成功");
    }

    @GetMapping("/queueTake")
    @Operation(summary = "队列元素获取")
    public CommonResult<List<InformText>> takeElement() {
        List<InformText> elements = new ArrayList<>();
        InformText moduleElement = pduQueueService.getElementFromQueue("pduModule");
        InformText qualityElement = pduQueueService.getElementFromQueue("pduQuality");
        elements.add(moduleElement);
        elements.add(qualityElement);
        return success(elements);
    }

//    @PostMapping("/file-upload")
//    public CommonResult<UsedOrderInfo> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        return success(testDataService.fileUpload(file));
//    }

//    @GetMapping("/fileList-page")
//    public CommonResult<PageResult<UsedOrderInfo>> getFileListPage(FileListPageReqVO pageReqVO) {
//        PageResult<UsedOrderInfo> pageResult = testDataService.getFileListPage(pageReqVO);
//        return success(pageResult);
//    }


}
