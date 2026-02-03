package cn.iocoder.yudao.module.bus.controller.admin.testdata;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.*;
import cn.iocoder.yudao.module.bus.entity.ModulesTest;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.module.bus.entity.UsedOrderInfo;
import cn.iocoder.yudao.module.bus.mapper.ModulesTestMapper;
import cn.iocoder.yudao.module.bus.mapper.TestDataMapper;
import cn.iocoder.yudao.module.bus.service.ModulesTestService;
import cn.iocoder.yudao.module.bus.service.BusQueueService;
import cn.iocoder.yudao.module.bus.service.TestDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.lang.Console.log;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Slf4j
@RestController
@RequestMapping("/bus")
@Tag(name = "母线 - 质检")
public class BusController {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private TestDataMapper testDataMapper;

    @Autowired
    private ModulesTestService modulesTestService;

    @Autowired
    private ModulesTestMapper modulesTestMapper;

    @Autowired
    private BusQueueService busQueueService;

    @PostMapping("/testData")
    @PermitAll
    public ResponseEntity<?> receiveTestData(@RequestBody TestData testData) {
        try {
            // 去除空格
            testData.setModuleSn(testData.getModuleSn().replaceAll("\\s+", ""));
            testData.setAllData(testData.toString());
            testDataMapper.insert(testData);
            if ("0".equals(testData.getLanguageSelect())) {
                InformText informText = new InformText();
                String msg = testData.getDevName() + "：设备（" + testData.getModuleSn() + "）质检" + (testData.getTestResult().equals("0") ? "失败" : "完成");
                informText.setMessage(msg);
                informText.setTestDate(testData.getStartTime());
                informText.setTitle("母线质检");
                busQueueService.addElementToQueue("busQuality", informText);
            }
        } catch (Exception e) {
            log("存储测试数据失败：" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("存储测试数据失败" + e.getMessage());
            // 如果保存失败，返回500状态码和错误信息
        }
        return ResponseEntity.ok("存储测试数据成功");
    }

    @PutMapping("/updateTestData")
    @Operation(summary = "更新成品质检数据")
    public CommonResult<String> updateTestData(@RequestBody TestData testData) {
        testDataService.updateTestData(testData);
        return success("更新成功");
    }

    @DeleteMapping("/deleteTestData")
    @Operation(summary = "删除成品质检数据")
    public CommonResult<String> deleteTestData(@RequestParam("id") Integer id) {
        testDataService.deleteTestData(id);
        return success("删除成功");
    }

    @DeleteMapping("/deleteBatchTestData")
    @Operation(summary = "批量删除成品质检数据")
    public CommonResult<String> deleteBatchTestData(@RequestParam("moduleSn") String moduleSn) {
        int count = testDataService.deleteBatchTestData(moduleSn);
        return success("成功删除" + count + "条数据");
    }


    @GetMapping("/testData-page")
    public CommonResult<PageResult<TestData>> getTestDataPage(TestDataPageReqVO pageReqVO) {
        PageResult<TestData> pageResult = testDataService.getTestDataPage(pageReqVO);
        return success(pageResult);
    }

    @PostMapping("/file-upload")
    public CommonResult<UsedOrderInfo> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return success(testDataService.fileUpload(file));
    }

    @GetMapping("/fileList-page")
    public CommonResult<PageResult<UsedOrderInfo>> getFileListPage(FileListPageReqVO pageReqVO) {
        PageResult<UsedOrderInfo> pageResult = testDataService.getFileListPage(pageReqVO);
        return success(pageResult);
    }

    @PostMapping("/report")
    @PermitAll
    public CommonResult<ReportRespVO> getReportInfo(@RequestBody ReportReqVO reqVO) {
        ReportRespVO respVO = testDataService.getReportInfo(reqVO);
        return success(respVO);

    }


    @GetMapping("/internal-report")
    public CommonResult<List<TestData>> getInternalReport(ReportReqVO reqVO) {
        List<TestData> pageResult = testDataService.getInternalReport(reqVO);
        return success(pageResult);
    }


    @PostMapping("/moduleTest")
    @PermitAll
    public ResponseEntity<?> receiveModulesTest(@RequestBody ModulesTest modulesTest) {
        try {
            // 去除空格
            modulesTest.setModuleSn(modulesTest.getModuleSn().replaceAll("\\s+", ""));
            modulesTest.setAllData(modulesTest.toString());
            modulesTestMapper.insert(modulesTest);
            InformText informText = new InformText();
            String msg = modulesTest.getModuleType() + "：设备（" + modulesTest.getModuleSn() + "）校准" + (modulesTest.getTestResult().equals("0") ? "失败" : "完成");
            informText.setTestDate(modulesTest.getTestTime());
            informText.setMessage(msg);
            informText.setTitle("母线校准");
            busQueueService.addElementToQueue("busModule", informText);

        } catch (Exception e) {
            log("存储测试数据失败：" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("存储测试数据失败" + e.getMessage());
            // 如果保存失败，返回500状态码和错误信息
        }
        return ResponseEntity.ok("存储测试数据成功");
    }

    @PutMapping("/updateModuleTest")
    @Operation(summary = "更新模块质检数据")
    public CommonResult<String> updateModuleTest(@RequestBody ModulesTest modulesTest) {
        modulesTestService.updateModuleTest(modulesTest);
        return success("更新成功");
    }

    @DeleteMapping("/deleteModuleTest")
    @Operation(summary = "删除模块质检数据")
    public CommonResult<String> deleteModuleTest(@RequestParam("id") Integer id) {
        modulesTestService.deleteModuleTest(id);
        return success("删除成功");
    }

    @DeleteMapping("/deleteBatchModuleTest")
    @Operation(summary = "批量删除模块质检数据")
    public CommonResult<String> deleteBatchModuleTest(@RequestParam("moduleSn") String moduleSn) {
        int count = modulesTestService.deleteBatchModuleTest(moduleSn);
        return success("成功删除" + count + "条数据");
    }


    @GetMapping("/moduleTest-page")
    @Operation(summary = "模块质检数据查询")
    public CommonResult<PageResult<ModulesTest>> getModulesTestPa(ModulesTestPageVO pageReqVO) {
        PageResult<ModulesTest> pageResult = modulesTestService.getModulesTestPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/queueTake")
    @Operation(summary = "队列元素获取")
    public CommonResult<List<InformText>> takeElement() {
        List<InformText> elements = new ArrayList<>();
        InformText moduleElement = busQueueService.getElementFromQueue("busModule");
        InformText qualityElement = busQueueService.getElementFromQueue("busQuality");
        elements.add(moduleElement);
        elements.add(qualityElement);
        return success(elements);
    }

}
