package cn.iocoder.yudao.module.bus.service;

import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.FileListPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ReportReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ReportRespVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.TestDataPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.TestDurationExcelVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.TestDurationReqVO;
import cn.iocoder.yudao.module.bus.entity.TestData;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bus.entity.UsedOrderInfo;
import com.google.zxing.WriterException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TestDataService {
    PageResult<TestData> getTestDataPage(TestDataPageReqVO pageReqVO);

    PageResult<UsedOrderInfo> getFileListPage(FileListPageReqVO pageReqVO);

    UsedOrderInfo fileUpload(MultipartFile file) throws IOException;

    void getOrderInfo(String[] dataStr, UsedOrderInfo usedOrderInfo, String baseUrl);

    void generateQRCode(String text, String baseUrl) throws IOException, WriterException;

    CTAnchor getAnchorWithGraphic(CTGraphicalObject ctGraphicalObject, String deskFileName, int width,
                                  int height, int leftOffset, int topOffset, boolean behind);

    void insertQRPicture(XWPFTableCell cell, String imgFile, int width, int height, int units)
            throws IOException, InvalidFormatException;

    ReportRespVO getReportInfo(ReportReqVO reqVO);

    List<TestData> getInternalReport(ReportReqVO reqVO);

    void updateTestData(TestData testData);

    void deleteTestData(Integer id);

    int deleteBatchTestData(String moduleSn);

    /**
     * 统计某序列号下交流耐压测试、绝缘测试、接地测试、功能测试的耗时
     *
     * @param reqVO 成品代码（必填）、模块序列号（选填）
     * @return 各测试项的耗时汇总
     */
    List<TestDurationExcelVO> getTestDurationList(TestDurationReqVO reqVO);
}
