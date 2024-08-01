package cn.iocoder.yudao.module.bus.controller.admin.testdata;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.FileListPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ReportReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ReportRespVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.TestDataPageReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.module.bus.entity.UsedOrderInfo;
import cn.iocoder.yudao.module.bus.mapper.TestDataMapper;
import cn.iocoder.yudao.module.bus.service.TestDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.util.List;

import static cn.hutool.core.lang.Console.log;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
@Slf4j
@RestController
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private TestDataService testDataService;

    @Autowired
    private TestDataMapper testDataMapper;

    @PostMapping("/testData")
    @PermitAll
    public ResponseEntity<?> receiveTestData(@RequestBody TestData testData) {
        try {
            testData.setAll_data(testData.toString());
            testDataMapper.insert(testData);

        } catch (Exception e) {
            log("存储测试数据失败：" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("存储测试数据失败" + e.getMessage());
            // 如果保存失败，返回500状态码和错误信息
        }
        return ResponseEntity.ok("存储测试数据成功");
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

}
