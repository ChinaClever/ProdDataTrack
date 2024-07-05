package cn.iocoder.yudao.module.bus.controller.admin.testdata;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.PageReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.module.bus.service.TestDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.io.IOException;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
@Slf4j
@RestController
@RequestMapping("/bus")
public class BusController {
    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private TestDataService testDataService;

    @PostMapping("/testData")
    @PermitAll
    public ResponseEntity<?> receiveTestData(@RequestBody TestData testData) {
        try {
            TestData testData1 = mongoTemplate.save(testData);
            return ResponseEntity.ok(testData1);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save data: " + e.getMessage());
            // 如果保存失败，返回500状态码和错误信息
        }
    }

    @GetMapping("/testData-page")
    @PermitAll
    public CommonResult<PageResult<TestData>> getTestDataPage(PageReqVO pageReqVO) throws IOException {
        PageResult<TestData> pageResult = testDataService.getTestDataPage(pageReqVO);
        return success(pageResult);
    }

    @PostMapping("/file-upload")
    public CommonResult<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return success(testDataService.fileUpload(file));
    }

}