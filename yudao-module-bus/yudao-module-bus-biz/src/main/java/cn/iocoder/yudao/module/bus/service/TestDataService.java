package cn.iocoder.yudao.module.bus.service;

import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.PageReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import com.google.zxing.WriterException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TestDataService {
    PageResult<TestData> getTestDataPage(PageReqVO pageReqVO) throws IOException;

    String fileUpload(MultipartFile file) throws IOException;

    void getOrderInfo(String[] dataStr);

    void generateQRCode(String text) throws IOException, WriterException;
}
