package cn.iocoder.yudao.module.bus.service;

import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.FileListPageReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ReportReqVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.ReportRespVO;
import cn.iocoder.yudao.module.bus.controller.admin.testdata.vo.TestDataPageReqVO;
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
}
